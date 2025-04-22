export class SSEParser {
	constructor(callback) {
		this.buffer = '';
		this.callback = callback;
	}

	feed(chunk) {
		this.buffer += chunk;

		while (true) {
			const index = this.buffer.indexOf('\n\n');
			if (index === -1) break;

			const event = this.buffer.slice(0, index);
			this.buffer = this.buffer.slice(index + 2);

			this.parseEvent(event);
		}
	}

	parseEvent(eventStr) {
		const lines = eventStr.split('\n');
		const event = {
			type: 'message',
			data: ''
		};

		lines.forEach(line => {
			const colonIndex = line.indexOf(':');
			if (colonIndex <= 0) return;

			const field = line.slice(0, colonIndex).trim();
			const value = line.slice(colonIndex + 1).trim();

			switch (field) {
				case 'event':
					event.type = value;
					break;
				case 'data':
					event.data += value + '\n';
					break;
			}
		});

		event.data = event.data.trimEnd();
		this.callback(event);
	}
}