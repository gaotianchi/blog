import type { Series, Tag } from "@/typing";
import { fakeSeries, fakeTags } from "./fakes";

export function getTags(): Tag[] {
	const tags = fakeTags();
	return tags;
}

export function getSeries(): Series[] {
	const sereises = fakeSeries();
	return sereises;
}
