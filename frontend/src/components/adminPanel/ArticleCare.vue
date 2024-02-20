<script setup lang="ts">
	import icons from "@/components/icons";
</script>
<template>
	<div class="parent-NJQTAg2j1g">
		<div class="parent-Vy7GxWhi1x">
			<slot name="cover"></slot>
		</div>
		<div class="parent-EJbXxWnjke">
			<div class="parent-V1C7xb3syx">
				<div class="parent-Ek3X-WnsJx">
					<slot name="title">
						Iriure nonummy vero sea facilisi feugiat takimata.
					</slot>
				</div>
				<div class="parent-4ke4Z-hjyl">
					<div class="parent-4JePW-2ike">
						<div class="child-41BOZ-2sJx">
							<component
								:is="icons.draft"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div class="child-41BOZ-2sJx">
							<component
								:is="icons.delete"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div class="child-41BOZ-2sJx">
							<component
								:is="icons.series"
								class="icon child-NkE1rZnskl"
							/>
						</div>
						<div class="child-41BOZ-2sJx">
							<component
								:is="icons.view"
								class="icon child-NkE1rZnskl"
							/>
						</div>
					</div>
					<div class="child-41BOZ-2sJx">
						<component
							:is="icons.author"
							class="icon child-NkE1rZnskl"
						/>
					</div>
				</div>
			</div>
			<div class="parent-N1GNlb3oke">
				<div class="parent-Nkzrbbnike">
					<div class="parent-N1rwLZ3jyl">Published</div>
					<div class="parent-VJ3zvZ2ske">·</div>
					<div class="parent-Vk5DLW2jJl">Tue May 12 2020</div>
					<div class="parent-4J0P8-hsJl">
						<div class="child-EyLCwWno1l">
							<span class="child-Ek0bDMhsJe">love</span>
						</div>
						<div class="child-EyLCwWno1l">
							<span class="child-Ek0bDMhsJe">you</span>
						</div>
					</div>
				</div>
				<div class="parent-41hBWb2ikx"></div>
			</div>
		</div>
	</div>
</template>
<style scoped>
	.parent-NJQTAg2j1g {
		width: 100%;
		height: 100px;
		display: flex;
		align-items: center;
		padding: 10px;
		border-radius: 10px;
		outline: #cacaca solid 1px;
	}
	.parent-NJQTAg2j1g:hover {
		outline: none;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
	}
	.parent-Vy7GxWhi1x {
		min-width: 80px;
		min-height: 80px;
		max-width: 80px;
		max-height: 80px;
		background-color: grey;
	}
	.parent-EJbXxWnjke {
		flex-grow: 1;
		display: flex;
		width: 100%;
		height: 100%;
		flex-direction: column;
		justify-content: space-between;
		padding: 10px;
	}
	.parent-V1C7xb3syx {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}
	.parent-Ek3X-WnsJx {
		flex-grow: 1;
		font-size: 17px;
		line-height: 17px;
	}
	.parent-4ke4Z-hjyl {
		display: flex;
		justify-content: flex-end;
	}
	.parent-4JePW-2ike {
		display: flex;
		justify-content: center;
	}
	.parent-N1GNlb3oke {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.parent-Nkzrbbnike {
		display: flex;
		align-items: center;
	}
	.child-NkE1rZnskl {
		width: 20px;
		height: 20px;
	}
	.child-41BOZ-2sJx {
		margin-left: 5px;
		cursor: pointer;
		height: 30px;
		width: 30px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.child-41BOZ-2sJx:hover {
		background-color: #cacaca8e;
		border-radius: 50%;
	}
	.parent-N1rwLZ3jyl {
		font-size: 16px;
		line-height: 16px;
		font-weight: bold;
		color: var(--second-color);
	}
	.parent-VJ3zvZ2ske {
		margin: 0 5px;
		font-size: 16px;
		font-weight: 1000;
		line-height: 14px;
		color: grey;
	}
	.parent-4J0P8-hsJl {
		display: flex;
		justify-content: space-evenly;
		align-items: center;
	}
	.child-EyLCwWno1l {
		height: 25px;
		width: fit-content;
		border-radius: 20px;
		margin-left: 10px;
		outline: lightgrey solid 1px;
		padding: 0 10px;
	}
	.child-Ek0bDMhsJe {
		font-size: 14px;
		font-weight: lighter;
	}
	.parent-Vk5DLW2jJl {
		font-size: 14px;
		line-height: 14px;
		font-weight: lighter;
	}
</style>
