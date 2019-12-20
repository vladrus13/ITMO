<template>
    <tr>
        <td>{{user.id}}</td>
        <td>{{user.login}}</td>
        <td>{{user.name}}</td>
    </tr>
</template>

<script>
    export default {
        props: ['user'],
        name: "User",
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        }
    }
</script>

<style scoped>

</style>