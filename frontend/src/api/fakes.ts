import { faker } from "@faker-js/faker";
import type { Series, Tag } from "@/typing";

export function fakeTags(count: number = 200): Tag[] {
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

export function fakeSeries(count: number = 15): Series[] {
	const serieses: Series[] = [];
	const author = faker.person.fullName();
	for (let i = 0; i < count; i++) {
		const series: Series = {
			id: i + 1,
			title: faker.lorem.sentence({ min: 6, max: 20 }),
			cover: "https://picsum.photos/50",
			author: author,
		};
		serieses.push(series);
	}
	return serieses;
}
