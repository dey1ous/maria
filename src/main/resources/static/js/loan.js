// Handle loan form submission
document.getElementById("loanForm").addEventListener("submit", async function (e) {
    e.preventDefault(); // Prevent the form from reloading the page
    const name = document.getElementById("name").value.trim(); // Get the submitted name
    const amount = parseFloat(document.getElementById("amount").value);

    if (!name || !amount || amount <= 0) {
        document.getElementById("loanResponse").innerText = "Please enter a valid name and loan amount.";
        return;
    }

    const isConfirmed = confirm(`Are you sure you want to apply for a loan of $${amount.toFixed(2)}?`);
    if (!isConfirmed) {
        return;
    }

    try {
        // Submit the loan application
        const response = await fetch('/api/loans', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, amount }),
        });

        if (!response.ok) {
            const errorText = await response.text();
            try {
                const errorJson = JSON.parse(errorText);
                throw new Error(errorJson.error || 'Unknown error');
            } catch {
                throw new Error(errorText || 'Unknown error');
            }
        }

        document.getElementById("loanResponse").innerText = "Loan submitted successfully!";
    } catch (error) {
        console.error("Error submitting loan:", error.message);
        document.getElementById("loanResponse").innerText = `Error submitting loan: ${error.message}`;
    }
});

// Fetch loan history based on name
async function fetchLoanHistoryByName(name) {
    if (!name.trim()) {
        alert("Please enter a valid name to fetch loan history.");
        return;
    }

    try {
        const response = await fetch(`/api/loans/by-name/${encodeURIComponent(name)}`);
        if (!response.ok) {
            throw new Error(`Error fetching loan history: ${response.statusText}`);
        }

        const loans = await response.json();
        const tableBody = document.getElementById("transactionTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (loans.length > 0) {
            loans.forEach((loan) => {
                const row = document.createElement("tr");

                const formattedDate = new Date(loan.applicationDate).toLocaleString();
                row.innerHTML = `
                    <td>${loan.name}</td>
                    <td>${loan.id}</td>
                    <td>$${loan.amount.toFixed(2)}</td>
                    <td>${loan.status}</td>
                    <td>${formattedDate}</td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const row = document.createElement("tr");
            row.innerHTML = "<td colspan='5'>No transactions found</td>";
            tableBody.appendChild(row);
        }
    } catch (error) {
        console.error("Error fetching transaction history:", error);
        alert("Failed to fetch transaction history. Please try again later.");
    }
}

// Handle fetching loan history
document.getElementById("fetchHistoryForm").addEventListener("submit", function (e) {
    e.preventDefault(); // Prevent form from reloading the page
    const name = document.getElementById("fetchName").value.trim(); // Get the name from the fetch history input
    fetchLoanHistoryByName(name); // Fetch history for the entered name
});
