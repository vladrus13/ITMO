<template>
    <!--suppress HtmlUnknownTag -->
    <body id="app">
    <Header :userId="userId" :users="users"/>
    <Middle :users="users" :posts="posts" :comments="comments"/>
    <Footer/>
    </body>
</template>

<script>
    import Header from './components/Header'
    import Middle from './components/Middle'
    import Footer from './components/Footer'

    export default {
        name: 'app',
        data: function () {
            return this.$root.$data;
        },
        components: {
            Header,
            Middle,
            Footer
        }, beforeCreate() {
            this.$root.$on("onLogout", () => {
                this.userId = null;
            });
            this.$root.$on("onRegister", (login, name) => {
                if (!/^[a-z]+$/.test(login) || login.length < 3 || login.length > 16) {
                    // login is invalid
                    if (!/^[a-z]+$/.test(login)) {
                        this.$root.$emit("onRegisterValidationError", "Login is uncorrected. Only small Latin!");
                    } else {
                        this.$root.$emit("onRegisterValidationError", "Login is uncorrected. Length must be on [3, 16]");
                    }
                } else {
                    if (name.length < 1 || name.length > 32) {
                        this.$root.$emit("onRegisterValidationError", "Name is uncorrected. Length must be on [1, 32]. Your - " + name.length);
                    } else {
                        let users = Object.values(this.users).filter(u => u.login === login);
                        if (users.length) {
                            this.$root.$emit("onRegisterValidationError", "Login is use");
                        } else {
                            const id = Math.max(...Object.keys(this.users)) + 1;
                            this.$set(this.users, id, {id: id, login: login, name: name, admin: false});
                            this.$root.$emit("onRegisterSuccess");
                        }
                    }
                }
            });
            this.$root.$on("onEnter", (login) => {
                let users = Object.values(this.users).filter(u => u.login === login);
                if (users.length) {
                    this.userId = users[0].id;
                    this.$root.$emit("onEnterSuccess");
                } else {
                    this.$root.$emit("onEnterValidationError", "Invalid login/password.");
                }
            });
            this.$root.$on("onAddPost", (title, text) => {
                if (this.userId) {
                    if (!title || title.length > 5) {
                        this.$root.$emit("onAddPostValidationError", "Title is invalid");
                    } else if (!text || text.length > 10) {
                        this.$root.$emit("onAddPostValidationError", "Text is invalid");
                    } else {
                        const id = Math.max(...Object.keys(this.posts)) + 1;
                        this.$set(this.posts, id, {
                            id: id,
                            userId: this.userId,
                            title: title,
                            text: text
                        })
                    }
                } else {
                    this.$root.$emit("onAddPostValidationError", "No access");
                }
            });
            this.$root.$on("onEditPost", (id, text) => {
                if (this.userId) {
                    if (!id) {
                        this.$root.$emit("onEditPostValidationError", "ID is invalid");
                    } else if (!text || text.length > 10) {
                        this.$root.$emit("onEditPostValidationError", "Text is invalid");
                    } else {
                        let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                        if (posts.length) {
                            posts.forEach((item) => {
                                item.text = text;
                            });
                        } else {
                            this.$root.$emit("onEditPostValidationError", "No such post");
                        }
                    }
                } else {
                    this.$root.$emit("onEditPostValidationError", "No access");
                }
            });
        }
    }
</script>

<style>
</style>
