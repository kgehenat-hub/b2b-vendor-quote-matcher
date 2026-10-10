import { useEffect, useState } from "react";

function Dashboard() {
    const [vendors, setVendors] = useState([]);
    const [error, setError] = useState("");

    const [selectedVendor, setSelectedVendor] = useState("");
    const [selectedFile, setSelectedFile] = useState(null);
    const [uploadMessage, setUploadMessage] = useState("");
    const [uploading, setUploading] = useState(false);

    const [sourceQuoteId, setSourceQuoteId] = useState("");
    const [targetQuoteId, setTargetQuoteId] = useState("");
    const [comparison, setComparison] = useState(null);
    const [comparisonError, setComparisonError] = useState("");
    const [comparing, setComparing] = useState(false);

    useEffect(() => {
        const fetchVendors = async () => {
            try {
                const token = localStorage.getItem("token");

                const response = await fetch(
                    "http://localhost:8080/api/vendors",
                    {
                        method: "GET",
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );

                if (!response.ok) {
                    throw new Error("Failed to fetch vendors");
                }

                const data = await response.json();
                setVendors(data);
            } catch (error) {
                console.error(error);
                setError(error.message);
            }
        };

        fetchVendors();
    }, []);

    const handleUpload = async () => {
        if (!selectedVendor || !selectedFile) {
            setUploadMessage("Please select a vendor and file.");
            return;
        }

        try {
            setUploading(true);
            setUploadMessage("");

            const token = localStorage.getItem("token");

            const formData = new FormData();

            formData.append("file", selectedFile);
            formData.append("vendorId", selectedVendor);

            const response = await fetch(
                "http://localhost:8080/api/quotes/upload",
                {
                    method: "POST",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    body: formData,
                }
            );

            if (!response.ok) {
                throw new Error("Quote upload failed");
            }

            const data = await response.json();

            console.log("Uploaded quote:", data);

            setUploadMessage(
                `Quote uploaded successfully. Quote ID: ${data.id}`
            );

            setSelectedFile(null);
            setSelectedVendor("");
        } catch (error) {
            console.error(error);
            setUploadMessage(error.message);
        } finally {
            setUploading(false);
        }
    };

    const handleCompare = async () => {
        if (!sourceQuoteId || !targetQuoteId) {
            setComparisonError("Please enter both Quote IDs.");
            return;
        }

        if (sourceQuoteId === targetQuoteId) {
            setComparisonError(
                "Source and Target Quote IDs must be different."
            );
            return;
        }

        try {
            setComparing(true);
            setComparisonError("");
            setComparison(null);

            const token = localStorage.getItem("token");

            const response = await fetch(
                `http://localhost:8080/api/price-comparison/${sourceQuoteId}/${targetQuoteId}`,
                {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            if (!response.ok) {
                throw new Error("Failed to compare quotes");
            }

            const data = await response.json();

            console.log("Comparison result:", data);

            setComparison(data);
        } catch (error) {
            console.error(error);
            setComparisonError(error.message);
        } finally {
            setComparing(false);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        window.location.reload();
    };

    const formatCurrency = (value) => {
        if (value === null || value === undefined) {
            return "₹0";
        }

        return `₹${Number(value).toLocaleString("en-IN")}`;
    };

    return (
        <div
            style={{
                minHeight: "100vh",
                background: "#f5f7fb",
                padding: "30px",
                fontFamily: "Arial, sans-serif",
            }}
        >
            {/* HEADER */}

            <div
                style={{
                    maxWidth: "1200px",
                    margin: "0 auto 30px",
                    background: "#ffffff",
                    padding: "24px 30px",
                    borderRadius: "12px",
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    boxShadow: "0 2px 10px rgba(0,0,0,0.06)",
                }}
            >
                <div>
                    <h1
                        style={{
                            margin: 0,
                            fontSize: "28px",
                            color: "#1f2937",
                        }}
                    >
                        Vendor Quote Matcher
                    </h1>

                    <p
                        style={{
                            margin: "6px 0 0",
                            color: "#6b7280",
                        }}
                    >
                        Compare vendor quotes and identify the best price
                    </p>
                </div>

                <button
                    onClick={handleLogout}
                    style={{
                        padding: "10px 18px",
                        border: "none",
                        borderRadius: "8px",
                        cursor: "pointer",
                        background: "#ef4444",
                        color: "white",
                        fontWeight: "bold",
                    }}
                >
                    Logout
                </button>
            </div>

            <div
                style={{
                    maxWidth: "1200px",
                    margin: "0 auto",
                }}
            >
                {/* VENDORS */}

                <div
                    style={{
                        background: "#ffffff",
                        padding: "25px",
                        borderRadius: "12px",
                        marginBottom: "25px",
                        boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                    }}
                >
                    <h2 style={{ marginTop: 0 }}>Vendors</h2>

                    {error && (
                        <p style={{ color: "#dc2626" }}>
                            {error}
                        </p>
                    )}

                    {vendors.length === 0 ? (
                        <p style={{ color: "#6b7280" }}>
                            No vendors found.
                        </p>
                    ) : (
                        <div
                            style={{
                                display: "grid",
                                gridTemplateColumns:
                                    "repeat(auto-fit, minmax(250px, 1fr))",
                                gap: "15px",
                            }}
                        >
                            {vendors.map((vendor) => (
                                <div
                                    key={vendor.id}
                                    style={{
                                        border: "1px solid #e5e7eb",
                                        borderRadius: "10px",
                                        padding: "15px",
                                    }}
                                >
                                    <strong>
                                        {vendor.companyName}
                                    </strong>

                                    <p
                                        style={{
                                            margin: "6px 0 0",
                                            color: "#6b7280",
                                        }}
                                    >
                                        {vendor.email}
                                    </p>
                                </div>
                            ))}
                        </div>
                    )}
                </div>

                {/* UPLOAD */}

                <div
                    style={{
                        background: "#ffffff",
                        padding: "25px",
                        borderRadius: "12px",
                        marginBottom: "25px",
                        boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                    }}
                >
                    <h2 style={{ marginTop: 0 }}>
                        Upload Vendor Quote
                    </h2>

                    <div
                        style={{
                            display: "grid",
                            gridTemplateColumns:
                                "repeat(auto-fit, minmax(250px, 1fr))",
                            gap: "20px",
                        }}
                    >
                        <div>
                            <label>
                                <strong>Select Vendor</strong>
                            </label>

                            <select
                                value={selectedVendor}
                                onChange={(e) =>
                                    setSelectedVendor(e.target.value)
                                }
                                style={{
                                    width: "100%",
                                    marginTop: "8px",
                                    padding: "11px",
                                    borderRadius: "8px",
                                    border: "1px solid #d1d5db",
                                }}
                            >
                                <option value="">
                                    Select vendor
                                </option>

                                {vendors.map((vendor) => (
                                    <option
                                        key={vendor.id}
                                        value={vendor.id}
                                    >
                                        {vendor.companyName}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div>
                            <label>
                                <strong>Select Quote File</strong>
                            </label>

                            <input
                                type="file"
                                accept=".pdf,.xlsx,.xls"
                                onChange={(e) =>
                                    setSelectedFile(e.target.files[0])
                                }
                                style={{
                                    display: "block",
                                    width: "100%",
                                    marginTop: "8px",
                                }}
                            />
                        </div>
                    </div>

                    <button
                        onClick={handleUpload}
                        disabled={uploading}
                        style={{
                            marginTop: "20px",
                            padding: "12px 22px",
                            border: "none",
                            borderRadius: "8px",
                            cursor: uploading
                                ? "not-allowed"
                                : "pointer",
                            background: "#2563eb",
                            color: "white",
                            fontWeight: "bold",
                            opacity: uploading ? 0.7 : 1,
                        }}
                    >
                        {uploading
                            ? "Uploading..."
                            : "Upload Quote"}
                    </button>

                    {uploadMessage && (
                        <p
                            style={{
                                marginTop: "15px",
                                color: uploadMessage.includes(
                                    "successfully"
                                )
                                    ? "#16a34a"
                                    : "#dc2626",
                                fontWeight: "bold",
                            }}
                        >
                            {uploadMessage}
                        </p>
                    )}
                </div>

                {/* COMPARISON */}

                <div
                    style={{
                        background: "#ffffff",
                        padding: "25px",
                        borderRadius: "12px",
                        marginBottom: "25px",
                        boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                    }}
                >
                    <h2 style={{ marginTop: 0 }}>
                        Compare Vendor Quotes
                    </h2>

                    <div
                        style={{
                            display: "grid",
                            gridTemplateColumns:
                                "repeat(auto-fit, minmax(220px, 1fr))",
                            gap: "20px",
                        }}
                    >
                        <div>
                            <label>
                                <strong>Source Quote ID</strong>
                            </label>

                            <input
                                type="number"
                                value={sourceQuoteId}
                                onChange={(e) =>
                                    setSourceQuoteId(e.target.value)
                                }
                                placeholder="Example: 44"
                                style={{
                                    width: "100%",
                                    boxSizing: "border-box",
                                    marginTop: "8px",
                                    padding: "11px",
                                    borderRadius: "8px",
                                    border: "1px solid #d1d5db",
                                }}
                            />
                        </div>

                        <div>
                            <label>
                                <strong>Target Quote ID</strong>
                            </label>

                            <input
                                type="number"
                                value={targetQuoteId}
                                onChange={(e) =>
                                    setTargetQuoteId(e.target.value)
                                }
                                placeholder="Example: 46"
                                style={{
                                    width: "100%",
                                    boxSizing: "border-box",
                                    marginTop: "8px",
                                    padding: "11px",
                                    borderRadius: "8px",
                                    border: "1px solid #d1d5db",
                                }}
                            />
                        </div>
                    </div>

                    <button
                        onClick={handleCompare}
                        disabled={comparing}
                        style={{
                            marginTop: "20px",
                            padding: "12px 22px",
                            border: "none",
                            borderRadius: "8px",
                            cursor: comparing
                                ? "not-allowed"
                                : "pointer",
                            background: "#111827",
                            color: "white",
                            fontWeight: "bold",
                            opacity: comparing ? 0.7 : 1,
                        }}
                    >
                        {comparing
                            ? "Comparing..."
                            : "Compare Quotes"}
                    </button>

                    {comparisonError && (
                        <p
                            style={{
                                marginTop: "15px",
                                color: "#dc2626",
                                fontWeight: "bold",
                            }}
                        >
                            {comparisonError}
                        </p>
                    )}
                </div>

                {/* RESULTS */}

                {comparison && (
                    <>
                        <div
                            style={{
                                display: "grid",
                                gridTemplateColumns:
                                    "repeat(auto-fit, minmax(220px, 1fr))",
                                gap: "20px",
                                marginBottom: "20px",
                            }}
                        >
                            <div
                                style={{
                                    background: "#ffffff",
                                    padding: "25px",
                                    borderRadius: "12px",
                                    boxShadow:
                                        "0 2px 10px rgba(0,0,0,0.05)",
                                }}
                            >
                                <p
                                    style={{
                                        margin: 0,
                                        color: "#6b7280",
                                    }}
                                >
                                    Source Quote Total
                                </p>

                                <h2>
                                    {formatCurrency(
                                        comparison.sourceQuoteTotal
                                    )}
                                </h2>
                            </div>

                            <div
                                style={{
                                    background: "#ffffff",
                                    padding: "25px",
                                    borderRadius: "12px",
                                    boxShadow:
                                        "0 2px 10px rgba(0,0,0,0.05)",
                                }}
                            >
                                <p
                                    style={{
                                        margin: 0,
                                        color: "#6b7280",
                                    }}
                                >
                                    Target Quote Total
                                </p>

                                <h2>
                                    {formatCurrency(
                                        comparison.targetQuoteTotal
                                    )}
                                </h2>
                            </div>

                            <div
                                style={{
                                    background: "#ecfdf5",
                                    padding: "25px",
                                    borderRadius: "12px",
                                    border: "1px solid #bbf7d0",
                                }}
                            >
                                <p
                                    style={{
                                        margin: 0,
                                        color: "#166534",
                                    }}
                                >
                                    Potential Savings
                                </p>

                                <h2
                                    style={{
                                        color: "#15803d",
                                    }}
                                >
                                    {formatCurrency(
                                        comparison.potentialSavings
                                    )}
                                </h2>
                            </div>
                        </div>

                        <div
                            style={{
                                background: "#ffffff",
                                padding: "25px",
                                borderRadius: "12px",
                                marginBottom: "25px",
                                boxShadow:
                                    "0 2px 10px rgba(0,0,0,0.05)",
                            }}
                        >
                            <h2 style={{ marginTop: 0 }}>
                                Comparison Summary
                            </h2>

                            <p>
                                <strong>Cheaper Quote:</strong>{" "}
                                <span
                                    style={{
                                        color: "#15803d",
                                        fontWeight: "bold",
                                    }}
                                >
                  {comparison.cheaperQuote}
                </span>
                            </p>
                        </div>

                        {/* PRODUCT TABLE */}

                        <div
                            style={{
                                background: "#ffffff",
                                padding: "25px",
                                borderRadius: "12px",
                                boxShadow:
                                    "0 2px 10px rgba(0,0,0,0.05)",
                                overflowX: "auto",
                            }}
                        >
                            <h2 style={{ marginTop: 0 }}>
                                Product Comparison
                            </h2>

                            <table
                                style={{
                                    width: "100%",
                                    borderCollapse: "collapse",
                                    minWidth: "750px",
                                }}
                            >
                                <thead>
                                <tr
                                    style={{
                                        background: "#f3f4f6",
                                    }}
                                >
                                    <th style={tableHeader}>
                                        Product
                                    </th>

                                    <th style={tableHeader}>
                                        Matched Product
                                    </th>

                                    <th style={tableHeader}>
                                        Source Price
                                    </th>

                                    <th style={tableHeader}>
                                        Target Price
                                    </th>

                                    <th style={tableHeader}>
                                        Difference
                                    </th>

                                    <th style={tableHeader}>
                                        Result
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                {comparison.items.map((item) => (
                                    <tr key={item.sourceItemId}>
                                        <td style={tableCell}>
                                            {item.sourceProduct}
                                        </td>

                                        <td style={tableCell}>
                                            {item.matchedProduct}
                                        </td>

                                        <td style={tableCell}>
                                            {formatCurrency(
                                                item.sourceUnitPrice
                                            )}
                                        </td>

                                        <td style={tableCell}>
                                            {formatCurrency(
                                                item.matchedUnitPrice
                                            )}
                                        </td>

                                        <td style={tableCell}>
                                            {formatCurrency(
                                                item.priceDifference
                                            )}
                                        </td>

                                        <td
                                            style={{
                                                ...tableCell,
                                                fontWeight: "bold",
                                                color:
                                                    item.cheaperOption ===
                                                    "SOURCE"
                                                        ? "#2563eb"
                                                        : item.cheaperOption ===
                                                        "MATCHED"
                                                            ? "#16a34a"
                                                            : "#6b7280",
                                            }}
                                        >
                                            {item.cheaperOption ===
                                            "SAME_PRICE"
                                                ? "Same Price"
                                                : item.cheaperOption ===
                                                "SOURCE"
                                                    ? "Source Cheaper"
                                                    : "Target Cheaper"}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}

const tableHeader = {
    textAlign: "left",
    padding: "14px",
    borderBottom: "2px solid #e5e7eb",
    color: "#374151",
};

const tableCell = {
    padding: "14px",
    borderBottom: "1px solid #e5e7eb",
    color: "#4b5563",
};

export default Dashboard;