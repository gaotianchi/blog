<template>
	<NodeViewWrapper>
		<figure class="editor mb-3 mt-3" @click="openModal" :class="align">
			<blockquote class="blockquote">
				<p>
					{{ node.attrs.quote }}
				</p>
			</blockquote>
			<figcaption class="blockquote-footer">
				{{ node.attrs.source }}
			</figcaption>
		</figure>
		<ModalComponent ref="blockquoteRef" title="编辑引言" @save-change="handleSaveChage">
			<template #body>
				<div class="form-floating mb-3">
					<textarea
						class="form-control"
						placeholder="说了什么？"
						:id="modalId + 'quote-content'"
						style="height: 100px"
						v-model="quote"
					></textarea>
					<label :for="modalId + 'quote-content'">说了什么？</label>
				</div>
				<div class="form-floating mb-3">
					<input
						type="text"
						class="form-control"
						:id="modalId + 'quote-source'"
						placeholder="引言来自于哪里？"
						v-model="source"
					/>
					<label :for="modalId + 'quote-source'">引言来自于哪里？</label>
				</div>
				<div class="mb-3 text-center">
					<div
						class="btn-group"
						role="group"
						aria-label="Basic radio toggle button group"
					>
						<input
							type="radio"
							class="btn-check"
							:id="modalId + 'btnradio1'"
							autocomplete="off"
							v-model="align"
							value="text-start"
						/>
						<label class="btn btn-outline-secondary" :for="modalId + 'btnradio1'">
							<i class="bi bi-text-left"></i>
						</label>

						<input
							type="radio"
							class="btn-check"
							:id="modalId + 'btnradio2'"
							autocomplete="off"
							v-model="align"
							value="text-center"
						/>
						<label class="btn btn-outline-secondary" :for="modalId + 'btnradio2'">
							<i class="bi bi-text-center"></i>
						</label>

						<input
							type="radio"
							class="btn-check"
							:id="modalId + 'btnradio3'"
							autocomplete="off"
							v-model="align"
							value="text-end"
						/>
						<label class="btn btn-outline-secondary" :for="modalId + 'btnradio3'">
							<i class="bi bi-text-right"></i>
						</label>
					</div>
				</div>
			</template>
		</ModalComponent>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
	import ModalComponent from '@/component/ModalComponent.vue';
	import { ref, watch } from 'vue';
	import { v4 as uuidv4 } from 'uuid';

	const props = defineProps(nodeViewProps);
	const blockquoteRef = ref();
	const quote = ref(props.node.attrs.quote);
	const source = ref(props.node.attrs.source);
	const align = ref<'text-start' | 'text-center' | 'text-end'>('text-start');

	const modalId = uuidv4();
	watch([quote, source, align], () => {
		updateAtrri();
	});

	const updateAtrri = () => {
		props.updateAttributes({
			quote: quote.value,
			source: source.value,
			align: align.value,
		});
	};
	const handleSaveChage = () => {
		blockquoteRef.value.hide();
	};
	const openModal = () => {
		blockquoteRef.value.show();
	};
</script>
<style>
	figure.editor {
		cursor: pointer;
		margin-top: 1rem;
		margin-bottom: 1rem;
	}
</style>
