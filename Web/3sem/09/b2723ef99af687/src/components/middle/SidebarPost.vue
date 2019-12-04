<template>
    <section>
        <div class="header">
            By {{users[post.userId].login}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a href="#"><a href="#page=MainPage" @click="changePage({page:'MainPage', another: String(post.id)})">View all</a></a>
        </div>
    </section>

</template>

<script>
    export default {
        props: ['users', 'post'],
        name: "SidebarPost",
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
