<template>
    <div class="posts">
        <Post v-for="post in viewPosts" :post="post" :users="users" :key="post.id"/>
    </div>
</template>

<script>
    import Post from "./Post/Post";

    export default {
        name: "Index",
        props: ['users', 'posts'],
        data: function () {
            return {
                page: {page: "Index"}
            }
        },
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
