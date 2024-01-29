import type { Tag } from "@/typing";
import { fakeTags } from "./fakes";

export function getTags(): Tag[] {
	const tags = fakeTags(200);
	return tags;
}
