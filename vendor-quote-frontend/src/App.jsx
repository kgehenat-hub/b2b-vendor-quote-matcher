import { useState } from "react";
import Login from "./Login";
import Dashboard from "./Dashboard";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(
      !!localStorage.getItem("token")
  );

  return (
      <div>
        {isLoggedIn ? (
            <Dashboard />
        ) : (
            <Login onLogin={() => setIsLoggedIn(true)} />
        )}
      </div>
  );
}

export default App;