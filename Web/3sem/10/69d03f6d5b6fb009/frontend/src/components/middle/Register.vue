<template>
    <div class="enter form-box">
        <div class="header">Register</div>
        <div class="body">
            <form @submit.prevent="onRegister">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" v-model="login"/>
                    </div>
                </div>

                <div class="field">
                    <div class="name">
                        <label for="name">Name</label>
                    </div>
                    <div class="value">
                        <input id="name" name="name" v-model="name"/>
                    </div>
                </div>

                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" type="password" name="password" v-model="password"/>
                    </div>
                </div>

                <div class="error">{{error}}</div>

                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function() {
            return {
                login: "",
                password: "",
                error: ""
            }
        },
        name: "Register",
        beforeCreate() {
            this.$root.$on("onRegisterValidationError", (error) => {
                this.error = error;
            });
        },
        beforeMount() {
            this.login = "";
            this.name = "";
            this.password = "";
            this.error = "";
        },

        methods: {
            onRegister: function () {
                this.$root.$emit("onRegister", this.login, this.password, this.name);
            }
        }
    }
</script>

<style scoped>

</style>
