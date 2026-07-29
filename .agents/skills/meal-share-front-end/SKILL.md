---
name: meal-share-front-end
description: meal-share-front-end skill
---

# Meal Share — Complete Frontend Redesign Prompt (Vue 3)

> Paste everything below this line into Cursor / v0 / Bolt (or any AI coding tool) as a single prompt. It's written to be self-contained.

---

## Role & Brief

You are a senior frontend designer/engineer rebuilding the **complete UI** for **Meal Share**, a community platform where neighbors post extra home-cooked or surplus food for others nearby to claim instead of it going to waste.

- **Audience:** home cooks with extra food, students, families, anyone who wants a free/cheap meal or wants to cut food waste in their neighborhood.
- **The page's single job:** make it obvious, in under 10 seconds of landing, whether to tap "Share a meal" or "Find a meal."
- **This is a full visual identity change.** Rebuild from scratch in Vue 3 — do not reuse or reference any previous glassmorphism styling.

---

## Design Direction: Warm, Food-Forward, Cozy

**Explicitly avoid the three looks AI tools default to regardless of brief:** (1) warm cream background (~#F4F1EA) + high-contrast serif + terracotta accent (~#D97757), (2) near-black background + single acid-green/vermilion accent, (3) broadsheet newspaper layout with hairline rules and zero border-radius. "Food-forward and warm" makes it tempting to land on look #1 exactly — don't. Use the palette below instead, which flips the light/dark relationship and uses gold + basil green rather than terracotta as the signature colors.

### Palette (define as CSS custom properties)

| Token | Hex | Use |
|---|---|---|
| `--bg-warm` | `#FBF6EE` | Warm ivory paper — content sections |
| `--bg-deep` | `#2E2013` | Roasted-coffee brown-black — header, hero, footer |
| `--color-primary` | `#E29B3E` | Marigold/turmeric gold — primary CTAs, key accents |
| `--color-secondary` | `#3F6B4A` | Basil green — freshness cues, dietary tags, secondary buttons |
| `--color-urgent` | `#B4472E` | Paprika red — used *sparingly* for "1 portion left" / "claim soon" only |
| `--text-dark` | `#2A2118` | Body text on light backgrounds |
| `--text-light` | `#F7EEDF` | Body text on dark backgrounds |

Alternate `--bg-warm` and `--bg-deep` between sections instead of one flat cream backdrop for the whole page — that alternation is part of what makes this not read as the default look.

### Typography

- **Display (headlines):** Something with real texture and personality — e.g. Bricolage Grotesque. Used at large size, tight tracking, for hero and section headers only.
- **Body:** A warm humanist sans — e.g. Karla — for all paragraph and UI copy. Readable, not neutral-to-the-point-of-generic.
- **Utility/data:** A monospace — e.g. IBM Plex Mono — for portion counts, distance ("0.4 mi"), and countdown timers. This gives listings a "recipe index card" feel.

### Layout & signature element

Build the whole design around **one signature idea**: meal listings rendered as **torn-edge recipe cards** in a loose, slightly-imperfect grid (not a rigid uniform grid) — like cards pinned to a community kitchen board. On hover, a card lifts ~4px with a soft shadow and tilts 1–2deg, like picking it up off a stack. Meals marked "hot / ready now" get a subtle looping steam-wisp animation above the card. A running counter ("1,240+ meals shared this month") appears in a hand-chalk accent style, like a tally on a kitchen chalkboard — clearly label this as sample/placeholder data to be wired to real numbers later.

Keep everything else quiet and disciplined around that one signature — don't decorate every section with the same trick.

### Motion

- Page load: soft staggered fade/rise-in on hero content (~150ms stagger, ~400ms duration). No springy/bouncy easing.
- Scroll: gentle reveal-on-scroll for the "How it works" steps.
- Hover: card lift + tilt as described above; steam-wisp loop only on "ready now" tags.
- Respect `prefers-reduced-motion`: disable steam/tilt/parallax, keep only simple opacity fades.

---

## Technical Requirements (Vue 3)

- Vue 3, `<script setup>`, Composition API throughout.
- Scoped `<style>` per component, driven by CSS custom properties defined once in a shared tokens file (not hardcoded hex values scattered across components).
- Suggested structure:

```
src/
  main.js
  App.vue
  assets/
    tokens.css
  components/
    layout/
      AppHeader.vue
      AppFooter.vue
      MobileNav.vue
    sections/
      HeroSection.vue
      HowItWorksSection.vue
      MealFeedSection.vue
      TrustSection.vue
      ImpactStats.vue
      Testimonials.vue
      FinalCta.vue
    meal/
      MealCard.vue
      MealFilterBar.vue
      PostMealForm.vue
    ui/
      BaseButton.vue
      BaseTag.vue
      BaseBadge.vue
```

- Optional: Pinia for meal-feed state and filters if this connects to real data later; otherwise local `ref`/`reactive` with mock data is fine.
- Mobile-first responsive breakpoints: 480px / 768px / 1024px / 1280px.
- Accessibility floor (non-negotiable): semantic HTML5 landmarks, visible keyboard focus rings on every interactive element, alt text on every meal photo, AA color contrast minimum, `prefers-reduced-motion` respected.

---

## Required Sections (in order)

1. **Header/Nav** — logo, "Browse meals" / "Share a meal" links, sign-in.
2. **Hero** — the single clearest statement of what the site does + both primary CTAs ("Share a meal", "Find a meal near me").
3. **How it works** — 3 steps: post → claim → meet & share.
4. **Live meal feed** — filterable grid (distance, dietary tags, pickup window) using the recipe-card signature.
5. **Meal card (detailed spec)** — photo, title, portions left, dietary tags, distance, pickup countdown, host name/avatar, "Claim this meal" button.
6. **Post-a-meal flow** — simple form: photo, title, portions, dietary tags, pickup window, location.
7. **Trust & safety** — host verification, ratings, basic food-safety guidelines.
8. **Impact stats** — meals shared, estimated food waste avoided (clearly flagged as placeholder numbers).
9. **Testimonials** — short community stories.
10. **Final CTA** — sign-up prompt.
11. **Footer** — links, social, contact.
12. **Mobile bottom nav** — Home / Browse / Post / Profile, since this is the kind of app people use on their phone standing in their kitchen.

---

## Content & Copy Voice

Warm, neighborly, plain language — not a startup pitch. Active voice, name buttons by what they do:

- "Share a meal" / "Claim this meal" — not "Submit."
- "1 portion left" — not "Limited availability."
- Empty state for no nearby meals: "Nothing posted near you yet — be the first to share." Not a vague error.

---

## Process — Build It Like This

1. First, restate a compact design-token plan back (colors, type pairing, layout concept, signature element) before writing any code.
2. Build section by section, following that plan exactly.
3. Before final delivery, self-critique against the checklist below.

## Self-Critique Checklist

- [ ] Responsive down to 375px width
- [ ] Visible keyboard focus states on every interactive element
- [ ] `prefers-reduced-motion` respected
- [ ] Does **not** land on cream+terracotta, near-black+acid, or broadsheet defaults
- [ ] Real, specific copy — no lorem ipsum, no generic SaaS phrasing
- [ ] Alt text on all meal images
- [ ] AA color contrast minimum on all text