import { mergeAttributes, Node, type NodeViewProps } from '@tiptap/core';
import QuoteWrapper from '../wrapper/QuoteWrapper.vue';
import { VueNodeViewRenderer, type Attributes } from '@tiptap/vue-3';
import type { Component } from 'vue';

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		quoteBlock: {
			setQuoteBlock: () => ReturnType;
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
				default:
					"I'm selfish, impatient and a little insecure. I make mistakes, I am out of control and at times hard to handle. But if you can't handle me at my worst, then you sure as hell don't deserve me at my best.",
			},
			source: {
				default: 'Marilyn Monroe',
			},
		};
	},
	parseHTML() {
		return [
			{
				tag: 'quote-block',
			},
		];
	},
	renderHTML({ HTMLAttributes }) {
		return [
			'figure',
			{ class: 'text-center' },
			['blockquote', { class: 'blockquote' }, ['p', {}, HTMLAttributes.quote]],
			['figcaption', { class: 'blockquote-footer' }, HTMLAttributes.source],
		];
	},

	addCommands() {
		return {
			setQuoteBlock:
				() =>
				({ commands }) => {
					return commands.insertContent({
						type: 'quoteBlock',
					});
				},
		};
	},
	addNodeView() {
		return VueNodeViewRenderer(QuoteWrapper as Component<NodeViewProps>);
	},
});
