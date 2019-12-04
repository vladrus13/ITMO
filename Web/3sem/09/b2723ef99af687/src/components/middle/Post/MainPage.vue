<template>
    <div class="fullpost">
        <Post :users="users" :post="findPost()"/>
        <Comment v-for="comment in viewComments" :users="users" :comment="comment" :key="comment.id"/>
    </div>
</template>

<script>
    import Post from "./Post"
    import Comment from "./Comment";

    export default {
        name: "MainPage",
        components: {Comment, Post},
        props: ['users', 'posts', 'comments', "another"],
        data: function () {
            return {
                page: {
                    page: "MainP",
                    another: "5"
                }
            }
        },
        computed: {
            viewComments: function () {
                var dater = Object.values(this.posts).find(element => String(element.id) === Object.values(this.another).join(""));
                return Object.values(this.comments).filter(comment => comment.postId === dater.id);
            }
        },
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        },
        methods: {
            findPost: function () {
                return Object.values(this.posts).find(element => String(element.id) === Object.values(this.another).join(""));
            }
        }
    }
</script>

<style scoped>

</style>