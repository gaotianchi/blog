import { User, UserManager, type OidcClientSettings } from 'oidc-client-ts';

class AuthService {
	userManager: UserManager;

	constructor() {
		const setting: OidcClientSettings = {
			authority: 'http://localhost:8070',
			client_id: 'blog-client',
			redirect_uri: 'http://localhost:8800/callback',
			response_type: 'code',
			scope: 'openid profile',
		};
		this.userManager = new UserManager(setting);
	}

	async login(): Promise<void> {
		try {
			await this.userManager.signinRedirect();
		} catch (error) {
			console.error('Error handling redirect callback:', error);
			throw error;
		}
	}

	async handleRedirectCallback(): Promise<User | null> {
		try {
			const user = await this.userManager.signinRedirectCallback();
			return user;
		} catch (error) {
			console.error('Error handling redirect callback:', error);
			throw error;
		}
	}
	async getUser(): Promise<User | null> {
		return this.userManager.getUser();
	}

	async logout(): Promise<void> {
		await this.userManager.signoutRedirect();
	}

	async isAuthenticated(): Promise<boolean> {
		const user = await this.getUser();
		if (!user || user.expired) {
			return false;
		} else {
			return true;
		}
	}
}

export default new AuthService();
