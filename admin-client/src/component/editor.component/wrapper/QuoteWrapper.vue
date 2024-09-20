<template>
	<NodeViewWrapper>
		<figure class="text-center mb-3">
			<blockquote class="blockquote" @click="handleQuoteClicked">
				<p>
					<textarea
						:class="{
							'form-control': isEditing.quote,
							'form-control-plaintext': !isEditing.quote,
						}"
						class=""
						aria-label="quote"
						name="quote"
						id="quote"
						rows="8"
						v-model="quoteModel"
						ref="textareaRef"
					></textarea>
				</p>
			</blockquote>
			<figcaption class="blockquote-footer">
				<input
					type="text"
					:class="{
						'form-control': isEditing.source,
						'form-control-plaintext': !isEditing.source,
					}"
					class="w-auto"
					name="source"
					id="source"
					aria-label="source"
					v-model="sourceModel"
					ref="sourceRef"
					@click="handleSourceClicked"
				/>
			</figcaption>
		</figure>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
	import { onBeforeUnmount, onMounted, reactive, ref, watch } from 'vue';

	const props = defineProps(nodeViewProps);
	const quoteModel = ref(props.node.attrs.quote);
	const sourceModel = ref(props.node.attrs.source);
	const textareaRef = ref<HTMLTextAreaElement>();
	const sourceRef = ref<HTMLInputElement>();
	const isEditing = reactive({
		quote: false,
		source: false,
	});
	const handleQuoteClicked = () => {
		isEditing.quote = true;
		textareaRef.value?.select();
	};
	const handleSourceClicked = () => {
		isEditing.source = true;
		sourceRef.value?.select();
	};
	const updateAtrri = () => {
		props.updateAttributes({
			quote: quoteModel.value,
			source: sourceModel.value,
		});
	};
	watch(quoteModel, () => {
		updateAtrri();
	});
	const handleClickOutside = (event: MouseEvent) => {
		if (textareaRef.value && !textareaRef.value.contains(event.target as Node)) {
			isEditing.quote = false;
		}
		if (sourceRef.value && !sourceRef.value.contains(event.target as Node)) {
			isEditing.source = false;
		}
	};
	onMounted(() => {
		document.addEventListener('click', handleClickOutside);
	});

	onBeforeUnmount(() => {
		document.removeEventListener('click', handleClickOutside);
	});
</script>
