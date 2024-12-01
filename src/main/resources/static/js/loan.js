// Fetch loan history based on user ID
const fetchLoanHistory = async (userId) => {
    try {
        if (!userId) {
            alert("Please enter a User ID to view loan history.");
            return;
        }

        const response = await fetch(`/api/loans/user/${userId}`); // API to get the user's loan history
        const loans = await response.json();

        const tableBody = document.getElementById("transactionTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (loans.length > 0) {
            loans.forEach((loan) => {
                const row = document.createElement("tr");
                // Format the application date properly
                const formattedDate = new Date(loan.applicationDate).toLocaleString();
                row.innerHTML = `
                    <td>${loan.id}</td>
                    <td>${loan.amount.toFixed(2)}</td>
                    <td>${loan.status}</td>
                    <td>${formattedDate}</td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const row = document.createElement("tr");
            row.innerHTML = "<td colspan='4'>No transactions found</td>";
            tableBody.appendChild(row);
        }
    } catch (error) {
        console.error("Error fetching transaction history:", error);
        alert("Failed to fetch transaction history.");
    }
};

// Handle loan application form submission
document.getElementById("loanForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const amount = document.getElementById("amount").value;

    // Validate the loan amount
    if (!amount || amount <= 0) {
        document.getElementById("loanResponse").innerText = "Please enter a valid loan amount.";
        return;
    }

    // Show confirmation dialog
    const isConfirmed = confirm(`Are you sure you want to apply for a loan of $${parseFloat(amount).toFixed(2)}?`);
    if (!isConfirmed) {
        return; // Exit if the user cancels the confirmation
    }

    try {
        // Send loan application request to the server
        const response = await fetch("/api/loans", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ amount: parseFloat(amount) }), // Format amount as a float
        });

        const result = await response.json();
        if (response.ok) {
            document.getElementById("loanResponse").innerText = "Loan submitted successfully!";
        } else {
            document.getElementById("loanResponse").innerText = `Error: ${result.message || "Failed to submit loan."}`;
        }
    } catch (error) {
        console.error("Error submitting loan:", error);
        document.getElementById("loanResponse").innerText = "Failed to submit loan.";
    }
});

// Fetch transaction history on button click
document.getElementById("historyForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const userId = document.getElementById("userId").value;
    fetchLoanHistory(userId);
});
