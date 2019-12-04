<template>
    <div class="middle">
        <Sidebar :posts="posts" :users="users"/>
        <main>
            <Index v-if="page.page === 'Index'" :users="users" :posts="posts"/>
            <Enter v-if="page.page === 'Enter'"/>
            <Register v-if="page.page === 'Register'"/>
            <AddPost v-if="page.page === 'AddPost'"/>
            <EditPost v-if="page.page === 'EditPost'"/>
            <Users v-if="page.page === 'Users'" :users="users"/>
            <MainPage v-if="page.page === 'MainPage'" :users="users" :posts="posts" :comments="comments" :another="this.page.another"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import AddPost from './middle/AddPost';
    import Sidebar from './middle/Sidebar';
    import EditPost from "./middle/EditPost";
    import Users from "./middle/Users"
    import MainPage from "./middle/Post/MainPage";

    export default {
        name: "Middle",
        props: ['users', 'posts', 'comments'],
        data: function () {
            return {
                page: {
                    page: "Index",
                    another: -1
                }
            }
        },
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            }
        },
        components: {
            EditPost,
            Index,
            Enter,
            Register,
            Sidebar,
            AddPost,
            Users,
            MainPage
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
            this.$root.$on("onRegisterSuccess", () => {
                this.page.page = "Enter";
            });
        }
    }
</script>

<style scoped>

</style>
