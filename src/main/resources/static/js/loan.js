document.addEventListener("DOMContentLoaded", function () {
    // Fetch loan history function
    const fetchLoanHistory = async (userId) => {
        try {
            if (!userId || userId <= 0) {
                alert("Please enter a valid User ID to view loan history.");
                return;
            }

            const tableBody = document.getElementById("transactionTable").querySelector("tbody");
            tableBody.innerHTML = "<tr><td colspan='4'>Loading...</td></tr>"; // Show loading message

            const response = await fetch(`/api/loans/user/${userId}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch data: ${response.status} ${response.statusText}`);
            }

            const loans = await response.json();
            tableBody.innerHTML = ""; // Clear loading message

            if (loans.length > 0) {
                loans.forEach((loan) => {
                    const row = document.createElement("tr");
                    const formattedDate = new Date(loan.application_date).toLocaleString(); // Format application date
                    row.innerHTML = `
                        <td>${loan.id}</td>
                        <td>${loan.amount.toFixed(2)}</td>
                        <td>${loan.status}</td>
                        <td>${formattedDate}</td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                tableBody.innerHTML = "<tr><td colspan='4'>No transactions found.</td></tr>";
            }
        } catch (error) {
            console.error("Error fetching transaction history:", error);
            alert("Failed to fetch transaction history. Please try again later.");
        }
    };

    // Handle loan application form submission
    const submitLoanApplication = async () => {
        const amount = parseFloat(document.getElementById("amount").value);
        const userId = parseInt(document.getElementById("userId").value, 10); // Get user ID from input field

        // Validate loan amount
        if (isNaN(amount) || amount <= 0) {
            document.getElementById("loanResponse").innerText = "Please enter a valid loan amount.";
            return;
        }

        const isConfirmed = confirm(`Are you sure you want to apply for a loan of $${amount.toFixed(2)}?`);
        if (!isConfirmed) return;

        try {
            const response = await fetch("/api/loans", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ amount, userId }), // Send amount and userId as JSON
            });

            const result = await response.json();
            if (response.ok) {
                document.getElementById("loanResponse").innerText = "Loan submitted successfully!";
                document.getElementById("loanForm").reset(); // Reset the form
                // After loan is submitted, fetch the updated transaction history
                fetchLoanHistory(userId);
            } else {
                document.getElementById("loanResponse").innerText = `Error: ${result.message || "Failed to submit loan."}`;
            }
        } catch (error) {
            console.error("Error submitting loan:", error);
            document.getElementById("loanResponse").innerText = "Failed to submit loan. Please try again later.";
        }
    };

    // Add event listeners
    document.getElementById("loanForm").addEventListener("submit", (e) => {
        e.preventDefault();
        submitLoanApplication();
    });

    document.getElementById("historyForm").addEventListener("submit", (e) => {
        e.preventDefault();
        const userId = parseInt(document.getElementById("userId").value, 10);
        fetchLoanHistory(userId);
    });
});
