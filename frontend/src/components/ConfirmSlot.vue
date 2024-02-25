<script setup lang="ts">
	const props = defineProps<{
		status: boolean;
		callback: CallableFunction;
	}>();
	const emits = defineEmits(["close"]);
	function submitDecision(decision: boolean): void {
		emits("close");
		if (decision) {
			props.callback();
		}
	}
</script>
<template>
	<Teleport to="body">
		<Transition>
			<div class="parent-N1J4Cd-iJg" v-if="status">
				<div class="parent-V1UVAObiyl">
					<div class="parent-NyoVCdZoJg">
						<slot name="header">Message</slot>
					</div>
					<div class="parent-E1JrCd-sye">
						<slot name="body"></slot>
					</div>
					<div class="parent-VJVBCOZokg">
						<button
							type="button"
							class="parent-VkKBAOWsJg child-N1SU0_-jyg"
							@click="submitDecision(false)"
						>
							<slot name="noMessage">No</slot>
						</button>
						<button
							type="button"
							class="parent-N1yLCdbike child-N1SU0_-jyg"
							@click="submitDecision(true)"
						>
							<slot name="yesMessage">Yes</slot>
						</button>
					</div>
				</div>
			</div>
		</Transition>
	</Teleport>
</template>
<style src="@/assets/confirm.css"></style>
