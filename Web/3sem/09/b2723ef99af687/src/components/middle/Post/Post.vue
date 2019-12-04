<template>
    <div class="post">
        <div class="title"><a href="#page=MainPage" @click="changePage({page:'MainPage', another: String(post.id)})">{{post.title}}</a></div>
        <div class="information">By {{users[post.userId].login}}</div>
        <div class="body">{{post.text}}</div>
    </div>
</template>

<script>
    export default {
        props: ['users', 'post'],
        name: "Post",
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        },
        methods: {
            changePage: function (page) {
                this.$root.$emit("onChangePage", page);
            }
        }
    }
</script>

<style scoped>

</style>