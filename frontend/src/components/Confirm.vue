<script setup lang="ts">
	import { confirmProp } from "@/store";
	import { defaultConfirm } from "@/defaults";
	function submitDecision(decision: boolean): void {
		if (decision) {
			confirmProp.callback();
		}
		Object.assign(confirmProp, { ...defaultConfirm });
	}
</script>
<template>
	<Teleport to="body">
		<Transition>
			<div class="parent-N1J4Cd-iJg" v-if="confirmProp.body.length > 0">
				<div class="parent-V1UVAObiyl">
					<div class="parent-NyoVCdZoJg">
						{{ confirmProp.header ?? "Message" }}
					</div>
					<div class="parent-E1JrCd-sye">{{ confirmProp.body }}</div>
					<div class="parent-VJVBCOZokg">
						<button
							type="button"
							class="parent-VkKBAOWsJg child-N1SU0_-jyg"
							@click="submitDecision(false)"
						>
							{{ confirmProp.noMessage ?? "No" }}
						</button>
						<button
							type="button"
							class="parent-N1yLCdbike child-N1SU0_-jyg"
							@click="submitDecision(true)"
						>
							{{ confirmProp.yesMessage ?? "Yes" }}
						</button>
					</div>
				</div>
			</div>
		</Transition>
	</Teleport>
</template>
<style src="@/assets/confirm.css"></style>
