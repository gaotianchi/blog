<template>
	<div class="row">
		<div class="col-9">
			<div class="tile">
				<div class="tile-title">文章图片</div>
				<div class="tile-body">
					<div class="container" v-masonry>
						<div
							v-masonry-tile
							class="m-1"
							:class="`'image-'${index}`"
							v-for="(u, index) in urls"
							:key="index"
						>
							<img :src="u" alt="image1" loading="lazy" width="300" />
						</div>
					</div>
				</div>
				<div class="tile-footer"></div>
			</div>
		</div>
		<div class="col">
			<div class="container z-3 position-sticky" style="top: 60px">
				<div class="row">
					<div class="tile">
						<div class="tile-title">头像</div>
						<div class="tile-body">
							<div class="row row-cols-5">
								<div class="col mb-3" v-for="u in avatars">
									<img :src="u" alt="" width="50" />
								</div>
							</div>
						</div>
						<div class="tile-footer"></div>
					</div>
				</div>
				<div class="row">
					<div class="tile">
						<div class="tile-title">系列封面</div>
						<div class="tile-body"></div>
						<div class="tile-footer"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script setup lang="ts">
	const urls = [
		'https://via.placeholder.com/300x250',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/300x250',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/300x250',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
		'https://via.placeholder.com/300x250',
		'https://via.placeholder.com/200x50',
		'https://via.placeholder.com/300x150',
		'https://via.placeholder.com/300x400',
	];
	const avatars = [
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
		'https://via.placeholder.com/200x200',
	];
</script>
