import { useState } from "react";

function Login({ onLogin }) {
    const [isRegistering, setIsRegistering] = useState(false);

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        setLoading(true);
        setMessage("");
        setError("");

        try {
            // =========================
            // REGISTER
            // =========================

            if (isRegistering) {
                const params = new URLSearchParams();

                params.append("name", name);
                params.append("email", email);
                params.append("password", password);

                const response = await fetch(
                    "http://localhost:8080/api/auth/register",
                    {
                        method: "POST",
                        headers: {
                            "Content-Type":
                                "application/x-www-form-urlencoded",
                        },
                        body: params.toString(),
                    }
                );

                const text = await response.text();

                if (!response.ok) {
                    throw new Error(
                        text || `Registration failed (${response.status})`
                    );
                }

                setMessage(
                    "Registration successful! Please login."
                );

                setIsRegistering(false);

                setName("");
                setEmail("");
                setPassword("");

                return;
            }

            // =========================
            // LOGIN
            // =========================

            const response = await fetch(
                "http://localhost:8080/api/auth/login",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        email,
                        password,
                    }),
                }
            );

            const text = await response.text();

            if (!response.ok) {
                throw new Error(
                    text || `Login failed (${response.status})`
                );
            }

            const data = JSON.parse(text);

            localStorage.setItem("token", data.token);

            console.log("JWT token stored successfully.");

            setMessage("Login successful!");

            onLogin();

        } catch (error) {
            console.error("Authentication error:", error);

            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const switchMode = () => {
        setIsRegistering(!isRegistering);

        setName("");
        setEmail("");
        setPassword("");

        setMessage("");
        setError("");
    };

    return (
        <div
            style={{
                minHeight: "100vh",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                background: "#f5f7fb",
                fontFamily: "Arial, sans-serif",
                padding: "20px",
            }}
        >
            <div
                style={{
                    width: "100%",
                    maxWidth: "420px",
                    background: "#ffffff",
                    padding: "35px",
                    borderRadius: "14px",
                    boxShadow:
                        "0 4px 20px rgba(0, 0, 0, 0.08)",
                    boxSizing: "border-box",
                }}
            >
                {/* HEADER */}

                <h1
                    style={{
                        textAlign: "center",
                        margin: "0 0 8px",
                        color: "#1f2937",
                    }}
                >
                    Vendor Quote Matcher
                </h1>

                <p
                    style={{
                        textAlign: "center",
                        color: "#6b7280",
                        marginBottom: "30px",
                    }}
                >
                    {isRegistering
                        ? "Create your account"
                        : "Login to your dashboard"}
                </p>

                {/* SUCCESS MESSAGE */}

                {message && (
                    <div
                        style={{
                            background: "#ecfdf5",
                            color: "#166534",
                            padding: "12px",
                            borderRadius: "8px",
                            marginBottom: "20px",
                            fontSize: "14px",
                        }}
                    >
                        {message}
                    </div>
                )}

                {/* ERROR MESSAGE */}

                {error && (
                    <div
                        style={{
                            background: "#fef2f2",
                            color: "#b91c1c",
                            padding: "12px",
                            borderRadius: "8px",
                            marginBottom: "20px",
                            fontSize: "14px",
                        }}
                    >
                        {error}
                    </div>
                )}

                {/* FORM */}

                <form onSubmit={handleSubmit}>
                    {/* NAME */}

                    {isRegistering && (
                        <div style={{ marginBottom: "18px" }}>
                            <label>
                                <strong>Name</strong>
                            </label>

                            <input
                                type="text"
                                value={name}
                                onChange={(e) =>
                                    setName(e.target.value)
                                }
                                placeholder="Enter your name"
                                required
                                style={inputStyle}
                            />
                        </div>
                    )}

                    {/* EMAIL */}

                    <div style={{ marginBottom: "18px" }}>
                        <label>
                            <strong>Email</strong>
                        </label>

                        <input
                            type="email"
                            value={email}
                            onChange={(e) =>
                                setEmail(e.target.value)
                            }
                            placeholder="Enter your email"
                            required
                            style={inputStyle}
                        />
                    </div>

                    {/* PASSWORD */}

                    <div style={{ marginBottom: "22px" }}>
                        <label>
                            <strong>Password</strong>
                        </label>

                        <input
                            type="password"
                            value={password}
                            onChange={(e) =>
                                setPassword(e.target.value)
                            }
                            placeholder="Enter your password"
                            required
                            minLength={6}
                            style={inputStyle}
                        />
                    </div>

                    {/* SUBMIT */}

                    <button
                        type="submit"
                        disabled={loading}
                        style={{
                            width: "100%",
                            padding: "12px",
                            border: "none",
                            borderRadius: "8px",
                            background: loading
                                ? "#93c5fd"
                                : "#2563eb",
                            color: "white",
                            fontWeight: "bold",
                            cursor: loading
                                ? "not-allowed"
                                : "pointer",
                            fontSize: "15px",
                        }}
                    >
                        {loading
                            ? "Please wait..."
                            : isRegistering
                                ? "Create Account"
                                : "Login"}
                    </button>
                </form>

                {/* SWITCH LOGIN / REGISTER */}

                <div
                    style={{
                        textAlign: "center",
                        marginTop: "22px",
                    }}
                >
          <span
              style={{
                  color: "#6b7280",
                  fontSize: "14px",
              }}
          >
            {isRegistering
                ? "Already have an account?"
                : "Don't have an account?"}
          </span>

                    <button
                        type="button"
                        onClick={switchMode}
                        style={{
                            marginLeft: "6px",
                            border: "none",
                            background: "none",
                            color: "#2563eb",
                            cursor: "pointer",
                            fontWeight: "bold",
                            fontSize: "14px",
                        }}
                    >
                        {isRegistering ? "Login" : "Register"}
                    </button>
                </div>
            </div>
        </div>
    );
}

const inputStyle = {
    width: "100%",
    boxSizing: "border-box",
    marginTop: "7px",
    padding: "12px",
    borderRadius: "8px",
    border: "1px solid #d1d5db",
    fontSize: "14px",
    outline: "none",
};

export default Login;