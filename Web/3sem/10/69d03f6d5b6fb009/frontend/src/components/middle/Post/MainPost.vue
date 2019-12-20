<template>
    <div class="fullpost">
        <Post :users="users" :post="findPost()" :comments="comments"/>
        <template v-if="user">
        <form @submit.prevent="onAdd">
            <div>
                <label for="text">
                    Text:
                </label>
                <textarea id="text" rows="4" v-model="text"></textarea>
            </div>
            <div class="error">{{error}}</div>
            <div>
                <input type="submit" value="Add"/>
            </div>
        </form>
        </template>
        <Comment v-for="comment in viewComments" :users="users" :comment="comment" :key="comment.id"/>
    </div>
</template>

<script>
    import Post from "./Post"
    import Comment from "./Comment";

    export default {
        name: "MainPage",
        components: {Comment, Post},
        props: ['users', 'posts', 'number', 'user', 'comments'],
        data: function () {
            return {
                page: {
                    page: "MainP",
                    number: "5"
                },
                text: "",
                postId: "",
                error: ""
            }
        },
        computed: {
            viewComments: function () {
                return Object.values(this.comments).filter(comment => comment.post.id == this.number);
            },
        },
        beforeMount() {
            this.text = this.error = "";
            this.$root.$on("onAddCommentValidationError", error => this.error = error);
        },
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        },
        methods: {
            findPost: function () {
                this.post = Object.values(this.posts).find(element => element.id == this.number);
                return this.post;
            },
            onAdd: function () {
                this.$root.$emit("onAddComment", this.post.id, this.text);
            }
        }
    }
</script>

<style scoped>
    label {
        display: block;
        margin-top: 1rem;
    }
    .title, textarea {
        width: 60%;
        box-sizing: border-box;
    }
    input[type='submit'] {
        margin-top: 1rem;
        width: 6rem;
    }
    .error {
        color: var(--error-color);
    }
</style>