<template>
    <div class="comment">
        <div class="information">By {{comment.user.login}}</div>
        <div class="body">{{comment.text}}</div>
    </div>
</template>

<script>
    export default {
        props: ['users', 'comment'],
        name: "Comment",
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
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