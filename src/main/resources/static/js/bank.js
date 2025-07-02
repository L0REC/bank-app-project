
/*API base URL*/
const API_BASE = '/api';

/*quick deposit function*/
async function quickDeposit() {
	try {
		const response = await fetch('${API_BASE}/deposit', {
			method: 'POST',
			header: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ amount: 10.0 })
		});

		const result = await response.json();

		if (result.success) {
			alert('Success: ${result.message}\nNew Balance: $${result.balance.toFixed(2)}');
			location.reload();
		} else {
			alert('Error: ${result.message}');
		}
	} catch (error) {
		alert('Error: Failed to process deposit');
		console.error('Deposit error:', error);
	}
}
/*quick withdraw function*/

