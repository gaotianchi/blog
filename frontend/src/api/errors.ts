export class APIError extends Error {
    target: string;
    message: string;
    status_code: number;
  
    constructor(target: string, message: string, status_code: number) {
      super(message);
      this.name = "APIError";
      this.target = target;
      this.message = message;
      this.status_code = status_code;
    }
  
    displayMessage(): string {
      return `Error occurred in ${this.target}: ${this.message} (Status Code: ${this.status_code})`;
    }
  }