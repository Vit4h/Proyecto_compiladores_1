import React, { useState } from "react";

export default function TextInputDisplay() {
  const [text, setText] = useState(""); // Estado para el texto ingresado
  const [fileContent, setFileContent] = useState(""); // Estado para el contenido del archivo
  const [file, setFile] = useState(null); // Estado para el archivo seleccionado
  const [output, setOutput] = useState([]); // Estado para la salida (tokens generados)
  const [error, setError] = useState(""); // Estado para manejar errores
  const [loading, setLoading] = useState(false); // Estado para manejar la carga

  // Manejar cambios en el campo de texto
  const handleTextChange = (e) => {
    setText(e.target.value);
    if (e.target.value) {
      setFile(null);
      setFileContent("");
    }
  };

  // Manejar cambios en la selección de archivos
  const handleFileChange = async (event) => {
    if (event.target.files.length > 0) {
      const selectedFile = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const content = e.target.result;
        setFileContent(content);
        setText("");
      };
      reader.readAsText(selectedFile);
      setFile(selectedFile);
    }
  };

  // Enviar el contenido al backend para análisis
  const handleAnalyze = async () => {
    const contentToAnalyze = fileContent || text;

    if (!contentToAnalyze.trim()) {
      setError("Por favor, ingresa un código fuente o carga un archivo.");
      return;
    }

    setLoading(true);
    setError("");

    try {
      console.log("Enviando código al backend:", contentToAnalyze); // Depuración
      const response = await fetch("http://localhost:8081/api/analyze", {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
          },
          body: JSON.stringify({ code: contentToAnalyze }), // Enviar el contenido como JSON
      });
  
      if (!response.ok) {
          throw new Error("Error al analizar el código");
      }
  
      const data = await response.json();
      console.log("Respuesta del backend:", data); // Depuración
      setOutput(data.tokens || []);
  } catch (err) {
      console.error("Error en la solicitud:", err); // Depuración
      setError(`Error: ${err.message}`);
      setOutput([]);
  } finally {
      setLoading(false);
  }
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "flex-start",
        minHeight: "100vh",
        backgroundColor: "#FFFFFF",
        padding: "20px",
      }}
    >
      <h1 style={{ fontSize: "20px", fontWeight: "bold", marginBottom: "8px" }}>
        Ingresar código fuente
      </h1>
      <input
        type="text"
        value={text}
        onChange={handleTextChange}
        style={{
          border: "1px solid #ccc",
          padding: "20px",
          borderRadius: "8px",
          width: "300px",
          fontSize: "10px",
          marginBottom: "12px",
        }}
        placeholder="Escribe aquí el código fuente"
        disabled={file !== null}
      />

      <h1 style={{ fontSize: "20px", fontWeight: "bold", marginBottom: "8px" }}>
        O carga el archivo del mismo aquí
      </h1>
      <input
        type="file"
        onChange={handleFileChange}
        style={{ marginBottom: "15px" }}
        disabled={text !== ""}
      />

      <div
        style={{
          marginTop: "25px",
          padding: "10px",
          backgroundColor: "rgb(182, 215, 228)",
          boxShadow: "0px 0px 8px rgba(0,0,0,0.1)",
          borderRadius: "5px",
          width: "150px",
          textAlign: "center",
          cursor: "pointer",
        }}
        onMouseEnter={(e) =>
          (e.target.style.backgroundColor = "rgb(0, 183, 255)")
        }
        onMouseLeave={(e) =>
          (e.target.style.backgroundColor = "rgb(182, 215, 228)")
        }
        onClick={handleAnalyze}
      >
        <p style={{ fontSize: "12px", fontWeight: "bold" }}>
          {loading ? "Analizando..." : "Pulsa aquí para utilizar el analizador Léxico"}
        </p>
      </div>

      {/* Mostrar errores */}
      {error && (
        <div style={{ color: "red", marginTop: "10px" }}>
          <strong>Error:</strong> {error}
        </div>
      )}

      <div
        style={{
          marginTop: "30px",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          gap: "500px",
        }}
      >
        <h1>Entrada</h1>
        <h1>Salida</h1>
      </div>

      <div
        style={{
          marginTop: "20px",
          display: "flex",
          justifyContent: "space-between",
          width: "80%",
          padding: "0 50px",
        }}
      >
        {/* Sección de Entrada */}
        <div
          style={{
            border: "1px solid #ccc",
            padding: "10px",
            width: "40%",
            minHeight: "200px",
            backgroundColor: "#f7f7f7",
          }}
        >
          <pre>{fileContent || text}</pre>
        </div>

        {/* Sección de Salida */}
        <div
          style={{
            border: "1px solid #ccc",
            padding: "10px",
            width: "40%",
            minHeight: "200px",
            backgroundColor: "#f7f7f7",
          }}
        >
          {output.length > 0 ? (
            <table style={{ width: "100%", borderCollapse: "collapse" }}>
              <thead>
                <tr>
                  <th style={{ border: "1px solid #ccc", padding: "8px" }}>Tipo</th>
                  <th style={{ border: "1px solid #ccc", padding: "8px" }}>Valor</th>
                  <th style={{ border: "1px solid #ccc", padding: "8px" }}>Línea</th>
                  <th style={{ border: "1px solid #ccc", padding: "8px" }}>Columna</th>
                </tr>
              </thead>
              <tbody>
                {output.map((token, index) => (
                  <tr key={index}>
                    <td style={{ border: "1px solid #ccc", padding: "8px" }}>{token.tipo}</td>
                    <td style={{ border: "1px solid #ccc", padding: "8px" }}>{token.valor}</td>
                    <td style={{ border: "1px solid #ccc", padding: "8px" }}>{token.linea}</td>
                    <td style={{ border: "1px solid #ccc", padding: "8px" }}>{token.columna}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>No hay tokens generados.</p>
          )}
        </div>
      </div>
    </div>
  );
}