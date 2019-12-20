<template>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <User v-for="user in viewUsers" :user="user" :key="user.id"/>
            </tbody>
        </table>
    </div>
</template>

<script>
    import User from "./User";
    export default {
        name: "Users",
        components: {User},
        props: ['users'],
        data: function () {
            return {
                page: "Index"
            }
        },
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        },
        computed: {
            viewUsers: function () {
                return Object.values(this.users).sort((a, b) => a.id - b.id);
            }
        },
    }
</script>

<style scoped>

</style>