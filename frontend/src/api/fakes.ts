import { faker } from "@faker-js/faker";
import type { Series, Tag } from "@/typing";
import { getUnixTime } from "date-fns";

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
	for (let i = 0; i < count; i++) {
		const series: Series = {
			id: i + 1,
			name: faker.lorem.sentence({ min: 6, max: 20 }),
			cover: "https://picsum.photos/50",
			author_id: i + 1,
		};
		serieses.push(series);
	}
	return serieses;
}
