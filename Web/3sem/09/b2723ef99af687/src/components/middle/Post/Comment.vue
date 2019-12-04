<template>
    <div class="comment">
        <div class="information">By {{users[comment.userId].login}}</div>
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

</style>