<template>
	<NodeViewWrapper>
		<figure class="mb-3" @click="openModal" :class="align">
			<blockquote class="blockquote">
				<p>
					{{ node.attrs.quote }}
				</p>
			</blockquote>
			<figcaption class="blockquote-footer">
				{{ node.attrs.source }}
			</figcaption>
		</figure>
		<ModalComponentNew
			modal-id="sfjkdjk"
			ref="blockquoteRef"
			title="编辑引言"
			@save-change="handleSaveChage"
		>
			<template #body>
				<div class="form-floating mb-3">
					<textarea
						class="form-control"
						placeholder="说了什么？"
						id="quote-content"
						style="height: 100px"
						v-model="quote"
					></textarea>
					<label for="quote-content">说了什么？</label>
				</div>
				<div class="form-floating mb-3">
					<input
						type="text"
						class="form-control"
						id="quote-source"
						placeholder="引言来自于哪里？"
						v-model="source"
					/>
					<label for="quote-source">引言来自于哪里？</label>
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
							name="btnradio"
							id="btnradio1"
							autocomplete="off"
							v-model="align"
							value="text-start"
						/>
						<label class="btn btn-outline-secondary" for="btnradio1">
							<i class="bi bi-text-left"></i>
						</label>

						<input
							type="radio"
							class="btn-check"
							name="btnradio"
							id="btnradio2"
							autocomplete="off"
							v-model="align"
							value="text-center"
						/>
						<label class="btn btn-outline-secondary" for="btnradio2">
							<i class="bi bi-text-center"></i>
						</label>

						<input
							type="radio"
							class="btn-check"
							name="btnradio"
							id="btnradio3"
							autocomplete="off"
							v-model="align"
							value="text-end"
						/>
						<label class="btn btn-outline-secondary" for="btnradio3">
							<i class="bi bi-text-right"></i>
						</label>
					</div>
				</div>
			</template>
		</ModalComponentNew>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
	import ModalComponentNew from '@/component/ModalComponentNew.vue';
	import { ref, watch } from 'vue';

	const props = defineProps(nodeViewProps);
	const blockquoteRef = ref();
	const quote = ref(props.node.attrs.quote);
	const source = ref(props.node.attrs.source);
	const align = ref<'text-start' | 'text-center' | 'text-end'>('text-start');

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
	figure {
		cursor: pointer;
	}
</style>
