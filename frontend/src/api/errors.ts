export class APIError extends Error {
	target: string;
	message: string;
	statusCode: number;

	constructor(target: string, message: string, statusCode: number) {
		super();
		this.name = "APIError";
		this.target = target;
		this.message = message;
		this.statusCode = statusCode;
	}

	displayMessage(): string {
		return `Error occurred in ${this.target}: ${this.message} (Status Code: ${this.statusCode})`;
	}
}
