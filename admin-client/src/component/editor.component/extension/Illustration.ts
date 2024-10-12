import type { IllustrationFloat } from '@/type/utlis';
import { Node } from '@tiptap/core';

export interface IllustrationOptions {}

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		illustration: {
			setIllustration: (options: {
				id: number;
				src: string;
				alt?: string;
				title?: string;
				align?: string;
			}) => ReturnType;
		};
	}
}

export const Illustration = Node.create<IllustrationOptions>({
	name: 'illustration',
	inline: true,
	group: 'inline',
	addOptions() {
		return {};
	},
	addAttributes() {
		return {
			id: {
				default: 0,
			},
			src: {
				default: '',
			},
			alt: {
				default: '',
			},
			title: {
				default: '',
			},
			align: {
				default: 'float-none',
			},
		};
	},
	renderHTML({ HTMLAttributes }) {
		return [
			'span',
			{
				class: `${HTMLAttributes.align} card illustration text-bg-dark me-3 ms-3 mb-1 mt-1 d-inline`,
				style: `
                cursor: pointer;
                width: 25rem;`,
			},
			[
				'img',
				{
					class: 'img-fluid card-img',
					title: HTMLAttributes.title,
					alt: HTMLAttributes.alt,
					src: HTMLAttributes.src,
					'data-id': HTMLAttributes.id,
				},
			],
			[
				'span',
				{ class: 'card-img-overlay' },
				['span', { class: 'card-title h5' }, HTMLAttributes.title],
				['br'],
				['span', { class: 'card-text' }, HTMLAttributes.alt],
			],
		];
	},
	parseHTML() {
		return [
			{
				tag: 'span',
				getAttrs: element => {
					if (!element.classList.contains('illustration')) {
						return false;
					}
					const img = element.querySelector('img');
					if (!img) {
						return false; // 如果没有找到 <img>，跳过这个节点
					}
					let align = 'float-none';
					if (element.classList.contains('float-start')) {
						align = 'float-start';
					} else if (element.classList.contains('float-end')) {
						align = 'float-end';
					}

					return {
						align: align, // 提取 `align` 属性
						src: img.getAttribute('src'),
						title: img.getAttribute('title'),
						alt: img.getAttribute('alt'),
						id: img.getAttribute('data-id'),
					};
				},
			},
		];
	},
	addCommands() {
		return {
			setIllustration:
				options =>
				({ chain }) => {
					return chain()
						.focus()
						.insertContent({
							type: this.name,
							attrs: options,
						})
						.run();
				},
		};
	},
});
