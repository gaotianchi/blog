import { Node } from '@tiptap/core';
import { NodeViewWrapper, NodeViewContent, VueNodeViewRenderer } from '@tiptap/vue-3';
import Example from './Example.vue';
declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		quoteBlock: {
			/**
			 * Set a blockquote node
			 */
			setQuote: (quote: string, source: string) => ReturnType;
		};
	}
}

export const QuoteExtension = Node.create({
	name: 'quoteBlock',

	group: 'block',

	content: 'block*',

	addAttributes() {
		return {
			quote: {
				default: '',
			},
			source: {
				default: '',
			},
		};
	},

	parseHTML() {
		return [
			{
				tag: 'figure.text-center',
			},
		];
	},

	renderHTML({ HTMLAttributes }) {
		return [
			'figure',
			{ class: 'text-center' },
			['blockquote', { class: 'blockquote' }, ['p', {}, HTMLAttributes.quote]],
			['figcaption', { class: 'blockquote-footer' }, `${HTMLAttributes.source}`],
		];
	},
	addCommands() {
		return {
			setQuote:
				(quote: string, source: string) =>
				({ commands }) => {
					return commands.insertContent({
						type: this.name, // 使用 quoteBlock 节点
						attrs: {
							quote,
							source,
						},
					});
				},
		};
	},
});
