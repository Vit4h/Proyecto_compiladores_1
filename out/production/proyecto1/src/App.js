import React from "react";
import TextInputDisplay from "./components/TextInputDisplay";

function App() {
  return (
    <div style={{ textAlign: "center", marginTop: "20px", backgroundColor: "lightblue" }}>
  <h1 style={{ fontSize: "28px", fontWeight: "bold", marginBottom: "20px" }}>Analizador Lexico</h1>
      <TextInputDisplay />
    </div>
  );
}

export default App;