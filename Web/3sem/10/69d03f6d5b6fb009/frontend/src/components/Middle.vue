<template>
    <div class="middle">
        <aside>
            <SidebarPost v-for="post in viewPosts" :post="post" :key="post.id"/>
        </aside>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :users="users" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <AddPost v-if="page === 'AddPost'"/>
            <MainPost v-if="page.toString().startsWith('MainPost')" :number="page.split('#')[1]" :posts="posts" :users="users" :user="user" :comments="comments"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import SidebarPost from './SidebarPost';
    import Users from "./middle/Users";
    import AddPost from "./middle/AddPost";
    import MainPost from "./middle/Post/MainPost";

    export default {
        name: "Middle",
        props: ["posts", "users", "user", "comments"],
        data: function () {
            return {
                page: "Index"
            }
        },
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            }
        },
        components: {
            MainPost,
            Index,
            Enter,
            Register,
            SidebarPost,
            Users,
            AddPost
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
            this.$root.$on("onRegisterSuccess", () => {
                this.page = "Index";
            });
        }
    }
</script>

<style scoped>

</style>
