import React, { useState } from "react";

export default function TextInputDisplay() {
  const [text, setText] = useState("");
  const [file, setFile] = useState(null);


  const handleTextChange = (e) => {
    setText(e.target.value);
    if (e.target.value) {
      setFile(null);
    }
  };

  const handleFileChange = (event) => {
    if (event.target.files.length > 0) {
      setFile(event.target.files[0]);
      setText("");
    }
  };

  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "flex-start", minHeight: "100vh", backgroundColor: "#FFFFFF", padding: "20px" }}>
      <h1 style={{ fontSize: "20px", fontWeight: "bold", marginBottom: "8px" }}>Ingresar codigo fuente</h1>
      <input
        type="text"
        value={text}
        onChange={handleTextChange}
        style={{
        border: "1px solid #ccc",
        padding: "20px", // Aumentar el padding para que el área sea más grande
        borderRadius: "8px", // Puedes hacer el borde más redondeado si lo deseas
        width: "300px", // Aumentar el ancho
        fontSize: "10px", // Aumentar el tamaño de la fuente
        marginBottom: "12px", // Aumentar el espacio entre los elementos
    }}
  placeholder="Escribe aquí el código fuente"
  disabled={file !== null}
/>

      <h1 style={{ fontSize: "20px", fontWeight: "bold", marginBottom: "8px" }}>O carga el archivo del mismo aquí</h1>
      <input
        type="file"
        onChange={handleFileChange}
        style={{ marginBottom: "15px" }}
        disabled={text !== ""}
      />

    
        <div 
        style={{ marginTop: "25px", padding: "10px",  backgroundColor: "rgb(182, 215, 228)", boxShadow: "0px 0px 8px rgba(0,0,0,0.1)", borderRadius: "5px", width: "150px", textAlign: "center" }}
        onMouseEnter={(e) => (e.target.style.backgroundColor = "rgb(0, 183, 255)")}
        onMouseLeave={(e) => (e.target.style.backgroundColor = "rgb(0, 200, 255)")}
        

        >
        <p style={{ fontSize: "12px", fontWeight: "bold",  }}>Pulsa aquí para utilizar el analizador Lexico</p>
    

    
      </div>

      <div style={{ marginTop:"30px", display: "flex", justifyContent: "space-between", alignItems: "center", gap: "500px" }}>
        <h1>Entrada</h1>
        <h1>Salida</h1>
      </div>


    </div>
    
  );
}

