<!--suppress HtmlUnknownAnchorTarget -->
<template>
    <div class="post">
        <div class="title"><a v-bind:href="'#page=MainPost#' + post.id" @click="changePage('MainPost#' + post.id)">{{post.title}}</a></div>
        <div class="information">By {{post.user.login}}</div>
        <div class="body">{{post.text}}</div>
        <div class="footer">
            <div class="left">
                <img src="../../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="../../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="../../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                {{countComments()}}
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['post', 'comments'],
        name: "Post",
        beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        },
        methods: {
            changePage: function (page) {
                this.$root.$emit("onChangePage", page);
            },
            countComments: function () {
                let counter = 0;
                Object.values(this.comments).filter(comment => {if (comment.post.id === this.post.id) counter++});
                return counter;
            }
        }
    }
</script>

<style scoped>

</style>