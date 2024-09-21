import { Node, type NodeViewProps } from '@tiptap/core';
import QuoteWrapper from '../wrapper/QuoteWrapper.vue';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
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
					'与怪物战斗的人，应当小心自己不要成为怪物。当你长久凝视深渊时，深渊也在凝视你。',
			},
			source: {
				default: '弗里德里希·威廉·尼采',
			},
			align: {
				default: '',
			},
		};
	},
	renderHTML({ HTMLAttributes }) {
		return [
			'figure',
			{ class: `${HTMLAttributes.align} mb-3 mt-3` },
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
