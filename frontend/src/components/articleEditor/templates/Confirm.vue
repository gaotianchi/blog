<script setup lang="ts">
	import { confirm } from "../localApi";
	import defaults from "../defaults";
	function submitDecision(decision: boolean): void {
		if (decision) {
			confirm.callback();
		}
		Object.assign(confirm, { ...defaults.confirm });
	}
</script>
<template>
	<Teleport to="body">
		<Transition>
			<div class="parent-N1J4Cd-iJg" v-if="confirm.body.length > 0">
				<div class="parent-V1UVAObiyl">
					<div class="parent-NyoVCdZoJg">
						{{ confirm.header ?? "Message" }}
					</div>
					<div class="parent-E1JrCd-sye">{{ confirm.body }}</div>
					<div class="parent-VJVBCOZokg">
						<button
							type="button"
							class="parent-VkKBAOWsJg child-N1SU0_-jyg"
							@click="submitDecision(false)"
						>
							{{ confirm.noMessage ?? "No" }}
						</button>
						<button
							type="button"
							class="parent-N1yLCdbike child-N1SU0_-jyg"
							@click="submitDecision(true)"
						>
							{{ confirm.yesMessage ?? "Yes" }}
						</button>
					</div>
				</div>
			</div>
		</Transition>
	</Teleport>
</template>
<style scoped>
	.parent-N1J4Cd-iJg {
		position: fixed;
		z-index: 9998;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		transition: opacity 0.3s ease;
	}
	.parent-V1UVAObiyl {
		min-width: 300px;
		max-width: 500px;
		margin: auto;
		padding: 20px 30px;
		background-color: #fff;
		border-radius: 2px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
		transition: all 0.3s ease;
	}
	.parent-NyoVCdZoJg {
		padding-bottom: 10px;
		font-size: larger;
		font-weight: bolder;
	}
	.parent-E1JrCd-sye {
		padding-bottom: 30px;
	}
	.parent-VJVBCOZokg {
		display: flex;
		justify-content: space-around;
		align-items: center;
	}
	.child-N1SU0_-jyg {
		width: 80px;
		height: 40px;
		border: none;
		border-radius: 10px;
		cursor: pointer;
		color: white;
		font-size: medium;
		font-weight: bold;
	}
	.parent-VkKBAOWsJg {
		background-color: rgba(128, 128, 128, 0.715);
	}
	.parent-N1yLCdbike {
		background-color: rgba(240, 61, 61, 0.792);
	}
	.v-enter-from,
	.v-leave-to {
		opacity: 0;
	}

	.v-enter-from .v-container,
	.v-leave-to .v-container {
		-webkit-transform: scale(1.1);
		transform: scale(1.1);
	}
</style>
