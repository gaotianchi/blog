<template>
    <NodeViewWrapper class="">
        <p class="card-text">
            This is a wider card with supporting text below as a natural lead-in to additional
            content. This content is a little bit longer. This is a wider card with supporting text
            below as a natural lead-in to additional content. This content is a little bit longer.
            This is a wider card with supporting text below as a natural lead-in to additional
        <div class="card text-bg-dark float-end d-inline-block" style="width: 25rem; display: inline-block;">
            <img src="https://p5.img.cctvpic.com/photoworkspace/contentimg/2023/03/30/2023033011303020756.jpg"
                class="img-fluid card-img" alt="..." />
            <div class="card-img-overlay">
                <h5 class="card-title">Card title</h5>
                <p class="card-text">
                    This is a wider card with supporting text below as a natural lead-in to
                    additional content. This content is a little bit longer.
                </p>
                <p class="card-text"><small>Last updated 3 mins ago</small></p>
            </div>
        </div>
        content. This content is a little bit longer. This is a wider card with supporting text
        below as a natural lead-in to additional content. This content is a little bit longer.
        </p>

        <p class="card-text">
            This is a wider card with supporting text below as a natural lead-in to additional
            content. This content is a little bit longer. This is a wider card with supporting text
            below as a natural lead-in to additional content. This content is a little bit longer.
            This is a wider card with supporting text below as a natural lead-in to additional
            content. This content is a little bit longer. This is a wider card with supporting text
            below as a natural lead-in to additional content. This content is a little bit longer.
        </p>
    </NodeViewWrapper>
</template>
<script setup lang="ts">
import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
import ModalComponentNew from '@/component/ModalComponentNew.vue';
import { ref, watch } from 'vue';

const props = ref(nodeViewProps);
</script>
