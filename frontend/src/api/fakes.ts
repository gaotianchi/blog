import { faker } from "@faker-js/faker";
import type { Tag } from "@/typing";

export function fakeTags(count: number): Tag[] {
	const tags: Tag[] = [];

	for (let i = 0; i < count; i++) {
		const tag: Tag = {
			id: i + 1,
			name: faker.lorem.word(),
		};
		tags.push(tag);
	}
	return tags;
}
