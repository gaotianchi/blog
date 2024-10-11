import { Node, type NodeViewProps } from '@tiptap/core';
import IllustrationNodeView from './IllustrationNodeView.vue';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import type { Component } from 'vue';
import { type ImageOptions, Image } from '@tiptap/extension-image';

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		Illustration: {
			setIllustration: () => ReturnType;
		};
	}
}

export const Illustration = Image.extend<ImageOptions>({
	name: 'Illustration',

	addAttributes() {
		return {
			...this.parent?.(),
			align: {
				default: 'float-none',
			},
			id: {
				default: null,
			},
		};
	},
	addNodeView() {
		return VueNodeViewRenderer(IllustrationNodeView as Component<NodeViewProps>);
	},
	renderHTML({ HTMLAttributes }) {
		return [
			'div',
			{
				class: `${HTMLAttributes.align} card text-bg-dark`,
				style: `
					cursor: pointer;
					margin: ${HTMLAttributes.align === 'float-none' ? 'auto' : '1rem'};
					display: ${HTMLAttributes.align === 'float-none' ? 'block' : 'inline-block'};
					width: ${HTMLAttributes.align === 'float-none' ? '30rem' : '25rem'};
				`,
				...HTMLAttributes,
			},
			[
				'img',
				{
					src:
						HTMLAttributes.src ||
						'https://p5.img.cctvpic.com/photoworkspace/contentimg/2023/03/30/2023033011303020756.jpg',
					class: 'img-fluid card-img',
					title: HTMLAttributes.title,
					alt: HTMLAttributes.alt,
				},
			],
			[
				'div',
				{ class: 'card-img-overlay' },
				['h5', { class: 'card-title' }, HTMLAttributes.title],
				['p', { class: 'card-text' }, HTMLAttributes.alt],
			],
		];
	},
	addCommands() {
		return {
			setIllustration:
				() =>
				({ commands }) => {
					return commands.insertContent({
						type: this.name,
					});
				},
		};
	},
});
