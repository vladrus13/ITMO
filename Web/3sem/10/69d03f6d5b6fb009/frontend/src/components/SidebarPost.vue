<template>
    <section>
        <div class="header">
            By {{post.user.name}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a v-bind:href="'#page=MainPost#' + post.id" @click="changePage('MainPost#' + post.id)">View All</a>
        </div>
    </section>

</template>

<script>
    export default {
        props: ['post'],
        name: "SidebarPost",
        methods: {
            changePage: function (page) {
                this.$root.$emit("onChangePage", page);
            }
        }
    }
</script>

<style scoped>

</style>
