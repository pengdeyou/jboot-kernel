import { defineUserConfig } from 'vuepress'
import { defaultTheme } from 'vuepress'

export default defineUserConfig({
  lang: 'zh-CN',
  title: 'JBoot-Kernel 4.0 使用手册',
  description: 'JBoot-Kernel是JBoot的一个强大工具集',
  theme: defaultTheme({
    navbar: [
      { text: '首页', link: '/' },
      { text: '模块文档', link: '/modules/' },
      { text: 'GitHub', link: 'https://github.com/pengdeyou/jboot-kernel' },
    ],
    sidebar: {
      '/modules/': [
        { text: '模块概览', link: '/modules/' },
        { text: 'kernel-bom', link: '/modules/kernel-bom/' },
        { text: 'kernel-boot', link: '/modules/kernel-boot/' },
        { text: 'kernel-cloud', link: '/modules/kernel-cloud/' },
        { text: 'kernel-launch', link: '/modules/kernel-launch/' },
        { text: 'kernel-secure', link: '/modules/kernel-secure/' },
        { text: 'kernel-test', link: '/modules/kernel-test/' },
        { text: 'kernel-toolkit', link: '/modules/kernel-toolkit/' },
        { text: 'kernel-crypto', link: '/modules/kernel-crypto/' },
        { text: 'kernel-datascope', link: '/modules/kernel-datascope/' },
        { text: 'kernel-develop', link: '/modules/kernel-develop/' },
        { text: 'kernel-excel', link: '/modules/kernel-excel/' },
        { text: 'kernel-i18n', link: '/modules/kernel-i18n/' },
        { text: 'kernel-loadbalancer', link: '/modules/kernel-loadbalancer/' },
        { text: 'kernel-log', link: '/modules/kernel-log/' },
        { text: 'kernel-mp', link: '/modules/kernel-mp/' },
        { text: 'kernel-oss', link: '/modules/kernel-oss/' },
        { text: 'kernel-redis', link: '/modules/kernel-redis/' },
        { text: 'kernel-report', link: '/modules/kernel-report/' },
        { text: 'kernel-social', link: '/modules/kernel-social/' },
        { text: 'kernel-swagger', link: '/modules/kernel-swagger/' },
        { text: 'kernel-transaction', link: '/modules/kernel-transaction/' },
        { text: 'kernel-cache', link: '/modules/kernel-cache/' },
        { text: 'kernel-tenant', link: '/modules/kernel-tenant/' },
      ],
    },
  }),
})