import { Node } from '@tiptap/core';

export interface IllustrationOptions {}

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		illustration: {
			setIllustration: (options: {
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
			src: {
				default: null,
			},
			alt: {
				default: null,
			},
			title: {
				default: null,
			},
			align: {
				default: 'float-none',
			},
		};
	},
	renderHTML({ HTMLAttributes }) {
		return [
			'div',
			{ class: 'card illustration text-bg-dark' },
			['img', { class: `${HTMLAttributes.align} card-img`, ...HTMLAttributes }],
			[
				'div',
				{ class: 'card-img-overlay' },
				['h5', { class: 'card-title' }, HTMLAttributes.title],
				['p', { class: 'card-text' }, HTMLAttributes.alt],
			],
		];
	},
	parseHTML() {
		return [
			{
				tag: 'div.card.illustration', // 匹配 <div> 标签，带有 `card` 和 `illustration` 类名
				getAttrs: element => {
					const img = element.querySelector('img');
					if (!img) {
						return false; // 如果没有找到 <img>，跳过这个节点
					}

					let align = 'float-none';
					if (img.classList.contains('float-start')) {
						align = 'float-start';
					} else if (img.classList.contains('float-end')) {
						align = 'float-end';
					}

					return {
						align: align, // 提取 `align` 属性
						src: img.getAttribute('src'),
						title: img.getAttribute('title'),
						alt: img.getAttribute('alt'),
					};
				},
			},
		];
	},
	addCommands() {
		return {
			setIllustration:
				options =>
				({ commands }) => {
					return commands.insertContent({
						type: this.name,
						attrs: options,
					});
				},
		};
	},
});
