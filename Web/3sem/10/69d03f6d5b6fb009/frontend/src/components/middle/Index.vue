<template>
    <div class="posts">
        <Post v-for="post in viewPosts" :post="post" :key="post.id" :comments="comments"/>
    </div>
</template>

<script>
    import Post from "./Post/Post";
    export default {
        name: "Index",
        props: ['users', 'posts', 'comments'],
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        },
        components: {
            Post
        }
    }
</script>

<style scoped>

</style>
