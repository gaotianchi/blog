<template>
	<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
		<input
			type="radio"
			class="btn-check"
			:id="id + 'btnradio1'"
			autocomplete="off"
			v-model="align"
			value="float-start"
		/>
		<label class="btn btn-outline-secondary" :for="id + 'btnradio1'">
			<i class="bi bi-text-left"></i>
		</label>

		<input
			type="radio"
			class="btn-check"
			:id="id + 'btnradio2'"
			autocomplete="off"
			v-model="align"
			value="float-none"
		/>
		<label class="btn btn-outline-secondary" :for="id + 'btnradio2'">
			<i class="bi bi-text-center"></i>
		</label>

		<input
			type="radio"
			class="btn-check"
			:id="id + 'btnradio3'"
			autocomplete="off"
			v-model="align"
			value="float-end"
		/>
		<label class="btn btn-outline-secondary" :for="id + 'btnradio3'">
			<i class="bi bi-text-right"></i>
		</label>
	</div>
</template>
<script setup lang="ts">
	import { v4 as uuidv4 } from 'uuid';

	const id = uuidv4();
	const align = defineModel<string>();
</script>
