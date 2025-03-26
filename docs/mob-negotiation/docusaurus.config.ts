import {themes as prismThemes} from 'prism-react-renderer';
import type {Config} from '@docusaurus/types';
import type * as Preset from '@docusaurus/preset-classic';

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

const config: Config = {
  title: 'Mob Negotiation | Documentation',
  tagline: 'Documentation for the Mob Negotiation plugin',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: 'https://zazsona.github.io/',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/Mob-Negotiation/',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'Zazsona', // Usually your GitHub org/user name.
  projectName: 'Mob-Negotiation', // Usually your repo name.

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      {
        docs: {
          routeBasePath: "/",
          sidebarPath: './sidebars.ts',
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/Zazsona/Mob-Negotiation/tree/master/docs/mob-negotiation/',
        },
        blog: false,
        theme: {
          customCss: './src/css/custom.css',
        },
      } satisfies Preset.Options,
    ],
  ],

  themeConfig: {
    // Replace with your project's social card
    image: 'img/docusaurus-social-card.jpg',
    navbar: {
      title: 'Mob Negotiation',
      logo: {
        alt: 'Mob Negotiation Logo',
        src: 'img/icon.png',
      },
      items: [
        {
          type: 'docSidebar',
          sidebarId: 'gameplaySidebar',
          position: 'left',
          label: 'Gameplay',
        },
        {
          type: 'docSidebar',
          sidebarId: 'configurationSidebar',
          position: 'left',
          label: 'Configuration',
        },
        {
          href: 'https://github.com/Zazsona/Mob-Negotiation',
          label: 'GitHub',
          position: 'right',
        },
      ],
    },
    footer: {
      style: 'dark',
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Gameplay',
              to: '/docs/intro',
            },
          ],
        },
        {
          title: 'Social',
          items: [
            {
              label: 'BlueSky',
              href: 'https://bsky.app/profile/zazsona.bsky.social',
            },
            {
              label: 'GitHub',
              href: 'https://github.com/Zazsona',
            }
          ],
        },
        {
          title: 'Other Plugins',
          items: [
            {
              label: 'DecorHeads',
              href: 'https://github.com/Zazsona/DecorHeads',
            },
            {
              label: 'PiTemp',
              href: 'https://github.com/Zazsona/PiTemp',
            },
            {
              label: 'Commemorations',
              href: 'https://github.com/Zazsona/Commemorations',
            },
          ],
        },
      ],
      //copyright: `Copyright © ${new Date().getFullYear()} Zazsona. Built with Docusaurus.`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
    },
  } satisfies Preset.ThemeConfig,
};

export default config;
