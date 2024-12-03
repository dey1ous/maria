// Fetch loan history based on user ID
async function fetchLoanHistory(userId) {
    try {
        if (!userId) {
            alert("Please enter a User ID to view loan history.");
            return;
        }

        const response = await fetch(`/api/loans/user/${userId}`);
        if (!response.ok) {
            throw new Error(`Error fetching loan history: ${response.statusText}`);
        }

        const loans = await response.json();

        const tableBody = document.getElementById("transactionTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (loans.length > 0) {
            loans.forEach(loan => {
                const row = document.createElement("tr");
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
        alert("Failed to fetch transaction history. Please try again later.");
    }
}

// Handle loan application form submission
document.getElementById("loanForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const amount = parseFloat(document.getElementById("amount").value);

    if (!amount || amount <= 0) {
        document.getElementById("loanResponse").innerText = "Please enter a valid loan amount.";
        return;
    }

    // Show confirmation dialog
    const isConfirmed = confirm(`Are you sure you want to apply for a loan of $${amount.toFixed(2)}?`);
    if (!isConfirmed) {
        return; // Exit if the user cancels the confirmation
    }

    try {
        const response = await fetch('/api/loans', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ amount }),
        });

        if (!response.ok) {
            const errorText = await response.text(); // Read response as text
            try {
                const errorJson = JSON.parse(errorText); // Try parsing as JSON
                throw new Error(errorJson.error || 'Unknown error');
            } catch {
                throw new Error(errorText || 'Unknown error');
            }
        }

        const data = await response.json();
        document.getElementById("loanResponse").innerText = "Loan submitted successfully!";
        console.log("Loan submitted successfully:", data);
    } catch (error) {
        console.error("Error submitting loan:", error.message);
        document.getElementById("loanResponse").innerText = `Error submitting loan: ${error.message}`;
    }
});

// Fetch transaction history on button click
document.getElementById("historyForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const userId = document.getElementById("userId").value;
    fetchLoanHistory(userId);
});
