import type { APIError } from "@/api/errors";
import type { Tag } from "./typing";
import { rootUrl } from "@/confit";

export async function getAllRemoteTags(): Promise<Tag[]> {
	const url = rootUrl + "/author/tags";
	const response = await fetch(url);
	if (response.status === 200) {
		const tagsData = await response.json();
		return tagsData as Tag[];
	} else {
		const errorData = await response.json();
		throw errorData.error as APIError;
	}
}
