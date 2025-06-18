// Handles dynamic form fields and API requests for the API Tester UI

document.addEventListener('DOMContentLoaded', () => {
    const endpointSelect = document.getElementById('endpoint');
    const contentTypeSelect = document.getElementById('contentType');
    const dynamicFields = document.getElementById('dynamicFields');
    const apiForm = document.getElementById('apiForm');
    const responseBox = document.getElementById('responseBox');

    function renderFields() {
        const endpoint = endpointSelect.value;
        const contentType = contentTypeSelect.value;
        dynamicFields.innerHTML = '';

        if (endpoint === '/test') {
            if (contentType === 'text/plain') {
                dynamicFields.innerHTML = `
                    <label for="plainText">Request Body (plain text)</label>
                    <textarea id="plainText" name="plainText" rows="3" placeholder="Type your message..."></textarea>
                `;
            } else if (contentType === 'application/json') {
                dynamicFields.innerHTML = `
                    <label for="jsonText">Request Body (JSON)</label>
                    <textarea id="jsonText" name="jsonText" rows="3" placeholder='{"question": "example"}'></textarea>
                `;
            }
        } else if (endpoint === '/add-device') {
            if (contentType === 'application/json') {
                dynamicFields.innerHTML = `
                    <label for="deviceName">Device Name</label>
                    <input type="text" id="deviceName" name="deviceName" placeholder="Device name..." />
                `;
            } else if (contentType === 'text/plain') {
                dynamicFields.innerHTML = `
                    <label for="plainText">Request Body (plain text)</label>
                    <textarea id="plainText" name="plainText" rows="3" placeholder="Type your message..."></textarea>
                `;
            }
        }
    }

    endpointSelect.addEventListener('change', renderFields);
    contentTypeSelect.addEventListener('change', renderFields);
    renderFields();

    apiForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        responseBox.textContent = 'Loading...';
        const endpoint = endpointSelect.value;
        const contentType = contentTypeSelect.value;
        let body = '';
        let headers = { 'Content-Type': contentType };

        if (endpoint === '/test') {
            if (contentType === 'text/plain') {
                body = document.getElementById('plainText').value;
            } else {
                // User must provide a JSON string
                body = document.getElementById('jsonText').value;
                try { JSON.parse(body); } catch { responseBox.textContent = 'Invalid JSON.'; return; }
            }
        } else if (endpoint === '/add-device') {
            if (contentType === 'application/json') {
                const deviceName = document.getElementById('deviceName').value;
                body = JSON.stringify({ 'device-name': deviceName });
            } else {
                body = document.getElementById('plainText').value;
            }
        }

        try {
            const res = await fetch(("http://192.168.1.63:5501" + endpoint), {
                method: 'POST',
                headers,
                body
            });
            const text = await res.text();
            responseBox.textContent = `Status: ${res.status}\n` + text;
        } catch (err) {
            responseBox.textContent = 'Error: ' + err;
        }
    });
});
